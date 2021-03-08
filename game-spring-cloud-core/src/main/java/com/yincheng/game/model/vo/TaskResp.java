package com.yincheng.game.model.vo;

import com.yincheng.game.model.po.Task;
import lombok.Data;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
public class TaskResp {

    private Integer id;

    private Long period;

    private String result;

    private Date startTime;

    private Date endTime;

    public TaskResp(Task task) {
        if (task != null) {
            this.id = task.getId();
            this.period = task.getPeriod();
            this.result = task.getResult();
            this.startTime = task.getStartTime();
            this.endTime = task.getEndTime();
        }
    }

}
