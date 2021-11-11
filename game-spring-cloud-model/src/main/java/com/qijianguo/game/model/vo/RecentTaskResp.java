package com.qijianguo.game.model.vo;

import com.qijianguo.game.model.po.Task;
import lombok.Data;

/**
 * WebSocket 通知用户开奖信息
 * @author qijianguo
 */
@Data
public class RecentTaskResp {

    /** 本期 */
    private TaskResp previous;
    /** 下期 */
    private TaskResp current;

    public RecentTaskResp(Task previous, Task current) {
        this.previous = new TaskResp(previous);
        this.current = new TaskResp(current);
    }

}
