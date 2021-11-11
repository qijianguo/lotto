package com.qijianguo.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel("任务请求")
public class JobReq {

    @ApiModelProperty(value = "任务组名", dataType = "String", required = true)
    private String groupName;
    @ApiModelProperty(value = "任务名", dataType = "String", required = true)
    private String jobName;
}
