package com.yincheng.game.service.task;

/**
 * @author qijianguo
 */
public abstract class AbstractTaskOperate {

    /**
     * 执行任务
     * @param taskType
     */
    void execute(TaskType taskType) {
        Task task = this.create(taskType);
        task.before();
        task.execute();
        task.after();
    }

    /**
     * 创建任务
     * @param taskType
     * @return
     */
    protected abstract Task create(TaskType taskType);
}
