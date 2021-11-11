package com.qijianguo.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@ApiModel("充值参数")
@Data
public class AccountPrepaidReq {

    @ApiModelProperty(value = "用户ID", dataType = "Integer", hidden = true, example = "0")
    private Integer userId;

    @ApiModelProperty(value = "充值积分, 必须大于2w", required = true, dataType = "Integer", example = "2000")
    private Integer credit;

    public boolean validate() {
        return userId != null && credit != null && credit > 0/* && credit >= 20000*/;
    }

}
