package com.qijianguo.game.model.vo;

import com.qijianguo.game.model.po.Account;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class RewardResponse {

    private Integer balance;

    private Integer reward;

    public RewardResponse(Account account) {
        if (account != null) {
            this.balance = account.getBalance();
            this.reward = account.getCredit();
        }
    }
}
