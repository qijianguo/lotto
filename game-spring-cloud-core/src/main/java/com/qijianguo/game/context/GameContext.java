package com.qijianguo.game.context;

import com.qijianguo.game.concurent.SpiderFlowThreadPoolExecutor;
import com.qijianguo.game.model.po.Task;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * 游戏上下文
 * @author qijianguo
 */
public class GameContext extends HashMap<String, Object> {

    private static final long serialVersionUID = 8379177178417619790L;

    private String id = UUID.randomUUID().toString().replace("-", "");

    private Integer gameId;

    /**
     * 流程执行线程
     */
    private SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor threadPool;

    /**
     * 是否在运行中
     */
    private volatile boolean running = true;

    private String result;

    private Task current;

    private Task nextTask;

    /**
     * Future队列
     */
    private LinkedBlockingQueue<Future<?>> futureQueue = new LinkedBlockingQueue<>();

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public LinkedBlockingQueue<Future<?>> getFutureQueue() {
        return futureQueue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Task getCurrent() {
        return current;
    }

    public void setCurrent(Task current) {
        this.current = current;
    }

    public Task getNextTask() {
        return nextTask;
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

    public void pause(int flowId, String event, String key, Object value) {}

    public void resume() {}

    public void stop() {}

}
