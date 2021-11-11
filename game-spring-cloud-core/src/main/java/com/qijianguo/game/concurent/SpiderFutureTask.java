package com.qijianguo.game.concurent;

import com.qijianguo.game.model.po.BetHistory;

import java.util.concurrent.FutureTask;

public class SpiderFutureTask<V> extends FutureTask {

    private SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor executor;

    private BetHistory node;

    public SpiderFutureTask(Runnable runnable, V result, BetHistory node, SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor executor) {
        super(runnable,result);
        this.executor = executor;
        this.node = node;
    }

    public SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor getExecutor() {
        return executor;
    }

    public BetHistory getNode() {
        return node;
    }
}
