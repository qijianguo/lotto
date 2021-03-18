package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.dao.mapper.AccountMapper;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.AccountDetail;
import com.yincheng.game.model.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author qijianguo
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private AccountDetailService accountDetailService;

    @Override
    public Account get(Integer userId) {
        Account one = lambdaQuery().eq(Account::getUserId, userId).one();
        if (one == null) {
            one = new Account(userId);
            //save(one);
        }
        return one;
    }

    @Override
    public Account prepaid(User user, Integer credit) {
        return null;
    }

    @Override
    public Account speed(User user, Integer credit) {
        // 校验余额
        Account account = get(user.getId());
        if (credit > account.getBalance()) {
            throw new BusinessException(EmBusinessError.ACCOUNT_INSUFFICIENT_BALANCE);
        }
        account.setBalance(account.getBalance() - credit);
        account.setUpdateTime(new Date());
        boolean success = updateById(account);
        if (!success) {
            throw new BusinessException(EmBusinessError.UNKNOW_ERROR);
        }
        // 消费明细
        accountDetailService.speed(account, credit);
        return account;
    }
}
