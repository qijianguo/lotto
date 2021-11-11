package com.qijianguo.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
@ApiModel("账户明细")
public class AccountDetailResp {

    @ApiModelProperty(value = "类型：1充值，2消费，3提现 4赠送，5中奖，-1 其他", required = true, dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "余额", required = true, dataType = "Integer")
    private Integer balance;
    @ApiModelProperty(value = "充值/消费积分", required = true, dataType = "Integer")
    private Integer credit;
    @ApiModelProperty(value = "手续费", required = true, dataType = "Integer")
    private Integer fee;
    @ApiModelProperty(value = "创建时间", required = true, dataType = "Date")
    private Date createTime;
    @ApiModelProperty(value = "更新时间", required = true, dataType = "Date")
    private Date updateTime;
    @ApiModelProperty(value = "状态确认：充值/消费默认为1，提现默认为0", required = true, dataType = "Integer")
    private Integer confirm;
    @ApiModelProperty(value = "是否成功：1成功，0失败或待处理", required = true, dataType = "Date")
    private Integer success;

}
