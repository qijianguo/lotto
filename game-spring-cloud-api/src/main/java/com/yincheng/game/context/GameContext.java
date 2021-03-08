package com.yincheng.game.context;

import com.yincheng.game.concurent.GameFlowThreadPoolExecutor.SubThreadPoolExecutor;
import com.yincheng.game.model.GameNode;
import com.yincheng.game.model.GameOutput;

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
    private SubThreadPoolExecutor threadPool;

    /**
     * 是否在运行中
     */
    private volatile boolean running = true;

    private GameNode gameNode;

    private String result;

    private Object outputMessage;

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

    public SubThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(SubThreadPoolExecutor threadPool) {
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

    public GameNode getGameNode() {
        return gameNode;
    }

    public void setGameNode(GameNode gameNode) {
        this.gameNode = gameNode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    //public <T>void addOutput(GameOutput<T> output) {}

    public void pause(int flowId, String event, String key, Object value) {}

    public void resume() {}

    public void stop() {}

}
