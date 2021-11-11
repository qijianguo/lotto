package com.qijianguo.game.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class HelloJobListenerImpl implements JobListener {

    @Override
    public String getName() {
        return "hello-job-listener-impl";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        // 即将执行
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        // 执行被否决
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        // 执行完成
    }
}
