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
    private TaskResp curr;
    /** 下期 */
    private TaskResp next;

    public TaskWsResp(Task curr, Task next) {
        this.curr = new TaskResp(curr);
        this.next = new TaskResp(next);
    }

}
