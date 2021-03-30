package com.yincheng.game.model.vo;

import com.yincheng.game.model.po.Task;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
@ApiModel("开奖结果")
public class TaskResp {

    @ApiModelProperty(value = "期号", required = true, dataType = "Long")
    private Long period;
    @ApiModelProperty(value = "结果", required = true, dataType = "String")
    private String result;
    @ApiModelProperty(value = "总和", required = true, dataType = "Integer")
    private Integer sum;
    @ApiModelProperty(value = "状态，0未开奖，1开奖", required = true, dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "开始下注时间", required = true, dataType = "Date")
    private Date startTime;
    @ApiModelProperty(value = "停止下注时间", required = true, dataType = "Date")
    private Date endTime;

    public TaskResp(Task task) {
        if (task != null) {
            this.period = task.getPeriod();
            this.result = task.getResult();
            this.sum = task.getSum();
            this.status = task.getStatus();
            this.startTime = task.getStartTime();
            this.endTime = task.getEndTime();
        }
    }

}
