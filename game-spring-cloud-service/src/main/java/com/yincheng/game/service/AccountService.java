package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.AccountDetail;

/**
 * @author qijianguo
 */
public interface AccountService extends IService<Account> {

    /**
     * 获取用户账户
     * @param userId
     * @return
     */
    Account get(Integer userId);

    /**
     * 充值
     * @param detail 充值积分
     * @return 账户余额
     */
    Account prepaid(AccountDetail detail);

    /**
     * 下注支出
     * @param detail 消费详情
     * @return 账户余额
     */
    Account betSpeed(AccountDetail detail);

    /**
     * 中奖收入
     * @param detail 收入详情
     * @return
     */
    Account betReward(AccountDetail detail);

    /**
     * 提现支出
     * @param detail 收入详情
     * @return
     */
    Account withdraw(AccountDetail detail);

    /**
     * 奖励收入
     * @param detail 收入详情
     * @return
     */
    Account giving(AccountDetail detail);

    Account increase(AccountDetail detail);

    Account decrease(AccountDetail detail);

}
