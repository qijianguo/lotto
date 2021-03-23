package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.dao.mapper.AccountMapper;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.AccountDetail;
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
        }
        return one;
    }

    @Override
    public Account prepaid(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.PREPAID);
        return increase(detail);
    }

    @Override
    public Account betSpeed(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.SPEED);
        return decrease(detail);
    }

    @Override
    public Account betReward(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.REWARD);
        return increase(detail);
    }

    @Override
    public Account withdraw(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.WITHDRAW);
        return decrease(detail);
    }

    @Override
    public Account giving(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.GIFT);
        return increase(detail);
    }

    public Account increase(AccountDetail detail) {
        if (detail == null || detail.getUserId() == null || detail.getCredit() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        int credit = detail.getCredit();
        Account account = get(detail.getUserId());
        // 是否是奖励
        if (AccountDetailType.GIFT.getType().equals(detail.getType())) {
            if (account.getStatus() != 0) {
                throw new BusinessException(EmBusinessError.REWARD_REPEATED_ERROR);
            }
            account.setStatus(1);
        }
        account.setBalance(account.getBalance() + credit);
        account.setUpdateTime(new Date());

        boolean success = saveOrUpdate(account);
        if (!success) {
            throw new BusinessException(EmBusinessError.UNKNOW_ERROR);
        }
        // 消费明细
        detail.setBalance(account.getBalance());
        accountDetailService.save(detail);
        return account;
    }

    private Account decrease(AccountDetail detail) {
        if (detail == null || detail.getUserId() == null || detail.getCredit() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        int credit = detail.getCredit();
        // 校验余额
        Account account = get(detail.getUserId());
        if (credit > account.getBalance()) {
            throw new BusinessException(EmBusinessError.ACCOUNT_INSUFFICIENT_BALANCE);
        }
        account.setBalance(account.getBalance() - credit);
        account.setUpdateTime(new Date());
        boolean success = updateById(account);
        if (!success) {
            throw new BusinessException(EmBusinessError.UNKNOW_ERROR);
        }
        detail.setBalance(account.getBalance());
        // 消费明细
        accountDetailService.save(detail);
        return account;
    }
}
