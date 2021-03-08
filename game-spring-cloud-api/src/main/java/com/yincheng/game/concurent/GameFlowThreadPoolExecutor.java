package com.yincheng.game.concurent;

import com.yincheng.game.model.GameNode;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qijianguo
 */
public class GameFlowThreadPoolExecutor {

    /**
     * 最大线程数
     */
    private int maxThreads;

    /**
     * 真正的线程池
     */
    private ThreadPoolExecutor executor;

    /**
     * 线程number计数器
     */
    private final AtomicInteger poolNum = new AtomicInteger(1);

    /**
     * ThreadGroup
     */
    private static final ThreadGroup GAME_FLOW_THREAD_GROUP = new ThreadGroup("game-flow-group");

    /**
     * 线程名称前缀
     */
    private static final String THREAD_POOL_NAME_PREFIX = "game-flow-";

    public GameFlowThreadPoolExecutor(int maxThreads) {
        super();
        this.maxThreads = maxThreads;
        this.executor = new ThreadPoolExecutor(maxThreads, maxThreads, 10, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(), runnable -> {
            // 重写线程名称
            return new Thread(GAME_FLOW_THREAD_GROUP, runnable, THREAD_POOL_NAME_PREFIX + poolNum.getAndDecrement());
        });
    }

    public Future<?> submit(Runnable runnable) {
        return this.executor.submit(runnable);
    }

    public SubThreadPoolExecutor createSubThreadPool(int threads, ThreadSubmitStrategy submitStrategy) {
        return new SubThreadPoolExecutor(Math.min(maxThreads, threads), submitStrategy);
    }

    /**
     * 子线程池
     */
    public class SubThreadPoolExecutor {

        /**
         * 最大线程数
         * 子线程数不能大于父线程数，如果超过则进入等待队列
         */
        private Integer threads;

        /**
         * 正在执行中的任务
         */
        private Future<?>[] futures;

        /**
         * 正在执行中的线程数
         */
        private AtomicInteger executing  = new AtomicInteger(0);

        /**
         * 是否在运行中
         */
        private boolean running = true;

        /**
         * 是否提交任务中
         */
        private volatile boolean submitting = false;

        /**
         * 线程提交策略
         */
        private ThreadSubmitStrategy submitStrategy;

        public SubThreadPoolExecutor(Integer threads, ThreadSubmitStrategy submitStrategy) {
            super();
            this.threads = threads;
            this.futures = new Future[threads];
            this.submitStrategy = submitStrategy;
        }

        /**
         * 异步提交任务
         */
        public <T>Future<T> submitAsync(Runnable runnable, T value, GameNode flow) {
            GameFutureTask<T> future  = new GameFutureTask<T>(() -> {
                try {
                    runnable.run();
                } finally {
                    // 正在执行的线程数减1
                    executing.decrementAndGet();
                }
            }, value, flow, this);
            submitStrategy.add(future);
            if (!submitting) {
                submitting = true;
                CompletableFuture.runAsync(this::submit);
            }
            synchronized (submitStrategy) {
                // 通知继续从集合中提取任务到线程池中
                submitStrategy.notifyAll();
            }
            return future;
        }

        private void submit() {
            while (running) {
                try {
                    synchronized (submitStrategy) {
                        // 如果任务集合为空，则等待提交
                        if (submitStrategy.isEmpty()) {
                            submitStrategy.wait();
                        }
                    }
                    while (!submitStrategy.isEmpty()) {
                        GameFutureTask<?> futureTask = submitStrategy.get();
                        // 如果没有空闲线程，且是在当前线程组提交的任务，则直接执行
                        if (index() == -1 && Thread.currentThread().getThreadGroup() == GAME_FLOW_THREAD_GROUP) {

                        } else {
                            // 等待有空闲线程时再提交
                            await();
                            // 提交任务到线程池中
                            futures[index()] = executor.submit(futureTask);
                        }
                    }
                } catch (Exception ignore) {
                }
            }
        }

        /**
         * 等待所有线程执行完成
         */
        public void awaitTermination() {
            while (executing.get() > 0) {
                removeDoneFuture();
            }
            running = false;
            // 当停止全部子任务后，唤醒提交线程任务使其结束
            synchronized (submitStrategy) {
                submitStrategy.notifyAll();
            }
        }

        /**
         * 清除已成的任务经
         */
        private void removeDoneFuture() {
            for (int i = 0; i < threads; i++) {
                try {
                    Future<?> future = futures[i];
                    if (future != null && future.get(10, TimeUnit.MICROSECONDS) == null) {
                        // 置为NULL
                        futures[i] = null;
                    }
                } catch (Exception ignore) {}
            }
        }

        /**
         * 得到空闲线程的索引
         * @return -1：无空闲线程
         */
        private int index() {
            for (int i = 0; i < threads; i++) {
                Future<?> future = futures[i];
                if (future == null || future.isDone()) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 等待有空闲线程
         */
        private void await() {
            if (index() == -1) {
                removeDoneFuture();
            }
        }
    }


}
