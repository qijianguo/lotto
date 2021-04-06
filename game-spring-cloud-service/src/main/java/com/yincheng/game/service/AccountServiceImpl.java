package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.dao.mapper.AccountMapper;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.AccountDetail;
import com.yincheng.game.model.vo.NotificationReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author qijianguo
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDetailService accountDetailService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AccountService accountService;

    @Override
    public Account get(Integer userId) {
        Account one = lambdaQuery().eq(Account::getUserId, userId).one();
        if (one == null) {
            one = Account.init(userId);
        }
        return one;
    }

    @Override
    public Account prepaid(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.PREPAID);
        return accountService.increase(detail);
    }

    @Override
    public Account betSpeed(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.SPEED);
        return accountService.decrease(detail);
    }

    @Override
    public Account betReward(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.REWARD);
        return accountService.increase(detail);
    }

    @Override
    public Account withdraw(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.WITHDRAW);
        Account account = accountService.decrease(detail);
        notificationService.withdraw(new NotificationReq(account.getUserId(), "Withdraw Rp" + account.getReward()));
        return account;
    }

    @Override
    public Account giving(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.GIFT);
        return accountService.increase(detail);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public synchronized Account increase(AccountDetail detail) {
        if (detail == null || detail.getUserId() == null || detail.getCredit() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        Account account = get(detail.getUserId());
        account.increase(detail);
        accountService.saveOrUpdate(account);
        accountDetailService.save(detail);
        return account;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public synchronized Account decrease(AccountDetail detail) {
        if (detail == null || detail.getUserId() == null || detail.getCredit() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        // 校验余额
        Account account = get(detail.getUserId());
        account.decrease(detail);
        accountService.updateById(account);
        // 消费明细
        accountDetailService.save(detail);
        return account;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Account increaseRedis(AccountDetail detail) {
        if (detail == null || detail.getUserId() == null || detail.getCredit() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        String key = "lock:account:" + detail.getUserId();
        boolean aBoolean = Optional.ofNullable(redisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS)).orElse(false);
        if (!aBoolean) {
            throw new BusinessException(EmBusinessError.REPEAT_COMMIT_ERROR);
        }
        try {
            Account account = get(detail.getUserId());
            // 是否是奖励
            if (AccountDetailType.GIFT.getType().equals(detail.getType())) {
                if (account.getStatus() != 0) {
                    throw new BusinessException(EmBusinessError.REWARD_REPEATED_ERROR);
                }
                account.setStatus(1);
            }
            account.increase(detail);
            accountService.saveOrUpdate(account);
            accountDetailService.save(detail);
            return account;
        } finally {
            redisTemplate.delete(key);
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Account decreaseRedis(AccountDetail detail) {
        if (detail == null || detail.getUserId() == null || detail.getCredit() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        String key = "lock:account:" + detail.getUserId();
        boolean aBoolean = Optional.ofNullable(redisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS)).orElse(false);
        if (!aBoolean) {
            throw new BusinessException(EmBusinessError.REPEAT_COMMIT_ERROR);
        }
        try {
            // 校验余额
            Account account = get(detail.getUserId());
            account.decrease(detail);
            accountService.updateById(account);
            // 消费明细
            accountDetailService.save(detail);
            return account;
        } finally {
            redisTemplate.delete(key);
        }
    }

}
