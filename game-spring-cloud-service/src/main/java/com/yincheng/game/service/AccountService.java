package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.AccountDetail;
import com.yincheng.game.model.po.User;

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
     * @param user 用户
     * @param credit 充值积分
     * @return 账户余额
     */
    Account prepaid(User user, Integer credit);

    /**
     * 消费
     * @param user 用户
     * @param credit 消费积分
     * @return 账户余额
     */
    Account speed(User user, Integer credit);

}
