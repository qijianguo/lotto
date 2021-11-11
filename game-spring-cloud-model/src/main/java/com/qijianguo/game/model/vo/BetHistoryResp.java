package com.qijianguo.game.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class BetHistoryResp {

    @ApiModelProperty(value = "期号", required = true, dataType = "Long")
    private Long period;
    @ApiModelProperty(value = "标的:A,B,C,D,SUM", required = true, dataType = "String")
    private String target;
    @ApiModelProperty(value = "下注", required = true, dataType = "String")
    private String bet;
    @ApiModelProperty(value = "下注积分", required = true, dataType = "Integer")
    private Integer credit;
    @ApiModelProperty(value = "odds", required = true, dataType = "Integer")
    private String odds;
    @ApiModelProperty(value = "费率", required = true, dataType = "Integer")
    private String fee;
    @ApiModelProperty(value = "result", required = true, dataType = "Integer")
    private String result;
    @ApiModelProperty(value = "状态：0未开奖，1已开奖 ", required = true, dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "描述", required = true, dataType = "String")
    private String description;
    @ApiModelProperty(value = "奖励金额", required = true, dataType = "Integer")
    private Integer reward;

}
