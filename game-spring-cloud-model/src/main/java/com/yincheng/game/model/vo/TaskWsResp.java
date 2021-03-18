package com.yincheng.game.model.vo;

import com.yincheng.game.model.po.Task;
import lombok.Data;

/**
 * WebSocket 通知用户开奖信息
 * @author qijianguo
 */
@Data
public class TaskWsResp {

    /** 本期 */
    private TaskResp task;
    /** 下期 */
    private TaskResp nextTask;

    public TaskWsResp(Task task, Task nextTask) {
        this.task = new TaskResp(task);
        this.nextTask = new TaskResp(nextTask);
    }

}
