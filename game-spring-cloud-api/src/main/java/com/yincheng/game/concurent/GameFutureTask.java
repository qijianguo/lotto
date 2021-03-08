package com.yincheng.game.concurent;

import com.yincheng.game.concurent.GameFlowThreadPoolExecutor.SubThreadPoolExecutor;
import com.yincheng.game.model.GameNode;

import java.util.concurrent.FutureTask;

/**
 * @author qijianguo
 */
public class GameFutureTask<V> extends FutureTask {

    private SubThreadPoolExecutor executor;

    private GameNode gameNode;

    public GameFutureTask(Runnable runnable, V value, GameNode flow, SubThreadPoolExecutor subThreadPoolExecutor) {
        super(runnable, value);
        this.executor = subThreadPoolExecutor;
        this.gameNode = flow;
    }
}
