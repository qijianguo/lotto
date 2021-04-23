package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
@ApiModel("消息通知返回")
public class NotificationResp {

    @ApiModelProperty(value = "头像", required = true, dataType = "String")
    private String avatar;
    @ApiModelProperty(value = "标题", required = true, dataType = "String")
    private String title;
    @ApiModelProperty(value = "时间", required = true, dataType = "Date")
    private Date time;
    @ApiModelProperty(value = "积分", required = true, dataType = "Integer")
    private Integer credit;
    @ApiModelProperty(value = "描述", required = true, dataType = "String")
    private String description;
}
