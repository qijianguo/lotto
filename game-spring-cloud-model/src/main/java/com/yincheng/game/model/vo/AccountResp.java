package com.yincheng.game.model.vo;

import com.yincheng.game.model.po.Account;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class AccountResp {

    private Integer balance;

    public AccountResp(Account account) {
        this.balance = account != null ? account.getBalance() : 0;
    }
}
