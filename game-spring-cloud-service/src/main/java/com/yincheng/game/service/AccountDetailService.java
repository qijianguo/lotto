package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.AccountDetail;

/**
 * @author qijianguo
 */
public interface AccountDetailService extends IService<AccountDetail> {

    AccountDetail prepaid(AccountDetail detail);

    AccountDetail speed(Account account, Integer credit);

    AccountDetail cashOut(AccountDetail detail);

}
