package com.yincheng.game.model.vo;

import com.yincheng.game.model.po.Account;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel("账户余额")
public class AccountResp {

    @ApiModelProperty(value = "状态, 0新用户，1老用户", required = true, dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "账户余额", required = true, dataType = "Integer")
    private Integer balance;

    public AccountResp(Account account) {
        this.balance = account != null ? account.getBalance() : 0;
    }
}
