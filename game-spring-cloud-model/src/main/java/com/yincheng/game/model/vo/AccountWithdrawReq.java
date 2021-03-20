package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class AccountWithdrawReq {

    @ApiModelProperty(value = "积分", required = true, dataType = "Integer")
    private Integer credit;

    public boolean validate() {
        return credit != null && credit > 0;
    }

}