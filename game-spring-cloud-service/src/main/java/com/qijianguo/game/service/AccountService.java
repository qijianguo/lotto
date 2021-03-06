package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qijianguo.game.model.po.Account;
import com.qijianguo.game.model.po.AccountDetail;
import com.qijianguo.game.model.vo.AccountWithdrawReviewReq;

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

    /**
     * 退回
     * @param detail
     * @return
     */
    Account returned(AccountDetail detail);

    /**
     * 增加积分
     * @param detail
     * @return
     */
    Account increase(AccountDetail detail);

    /**
     * 减少积分
     * @param detail
     * @return
     */
    Account decrease(AccountDetail detail);

    /**
     * 提现审核
     * @param req
     */
    void review(AccountWithdrawReviewReq req);

}
