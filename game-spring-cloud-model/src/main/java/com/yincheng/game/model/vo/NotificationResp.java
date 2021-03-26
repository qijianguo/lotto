package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel("消息通知返回")
public class NotificationResp {
    @ApiModelProperty(value = "标题", required = true, dataType = "String")
    private String title;
    @ApiModelProperty(value = "时间", required = true, dataType = "String")
    private String time;
    @ApiModelProperty(value = "描述", required = true, dataType = "String")
    private String description;
    @ApiModelProperty(value = "头像", required = true, dataType = "String")
    private String avatar;
}
