package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qijianguo.game.common.exception.BusinessException;
import com.qijianguo.game.common.exception.EmBusinessError;
import com.qijianguo.game.model.enums.NotificationType;
import com.qijianguo.game.model.po.Account;
import com.qijianguo.game.dao.mapper.AccountMapper;
import com.qijianguo.game.model.enums.AccountDetailType;
import com.qijianguo.game.model.po.AccountDetail;
import com.qijianguo.game.model.po.UserBank;
import com.qijianguo.game.model.vo.AccountWithdrawReviewReq;
import com.qijianguo.game.model.vo.NotificationReq;
import com.qijianguo.game.model.vo.SpWithdrawReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private SinopayDetailService sinopayDetailService;
    @Autowired
    private UserBankService userBankService;

    @Override
    public Account get(Integer userId) {
        Account one = lambdaQuery().eq(Account::getUserId, userId).one();
        if (one == null) {
            one = Account.init(userId);
        }
        return one;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Account prepaid(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.PREPAID);
        return accountService.increase(detail);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Account betSpeed(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.SPEED);
        return accountService.decrease(detail);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Account betReward(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.REWARD);
        return accountService.increase(detail);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Account withdraw(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.WITHDRAW);
        Account account = accountService.decrease(detail);
        notificationService.withdraw(NotificationReq.create(account, NotificationType.WITHDRAW));
        return account;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Account giving(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.GIFT);
        return accountService.increase(detail);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Account returned(AccountDetail detail) {
        detail.setDetailType(AccountDetailType.RETURN);
        return accountService.increase(detail);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Account increase(AccountDetail detail) {
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
    public Account decrease(AccountDetail detail) {
        if (detail == null || detail.getUserId() == null || detail.getCredit() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        // ????????????
        Account account = get(detail.getUserId());
        account.decrease(detail);
        accountService.updateById(account);
        // ????????????
        accountDetailService.save(detail);
        return account;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void review(AccountWithdrawReviewReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        AccountDetail detail = accountDetailService.getById(req.getAccountDetailId());
        if (detail == null) {
            throw new BusinessException(EmBusinessError.SOURCE_NOT_FOUND);
        }
        // ???????????????????????????
        UserBank bank = userBankService.getLatestAddOne(detail.getUserId());
        if (bank == null) {
            throw new BusinessException(EmBusinessError.WITHDRAW_BANK_NOT_FOUND);
        }
        // ?????????????????????????????????
        Integer confirm = detail.getConfirm();
        Integer success = detail.getSuccess();
        if (confirm == 1 || success == 1) {
            throw new BusinessException(EmBusinessError.WITHDRAW_ALREADY_REVIEWED);
        }
        // ??????????????????
        boolean pass = req.getPass() == 1;
        // ????????????,
        detail.setConfirm(1);
        if (pass) {
            // ????????????
            detail.setSuccess(1);
            boolean updated = accountDetailService.updateById(detail);
            if (!updated) {
                throw new BusinessException(EmBusinessError.UNKNOW_ERROR);
            }
            // ????????????????????????????????????
            sinopayDetailService.disburse(detail.getUserId(), detail.getId(), SpWithdrawReq.create(bank, String.valueOf(detail.getCost())));
        } else {
            // ????????????
            detail.setSuccess(0);
            boolean updated = accountDetailService.updateById(detail);
            if (!updated) {
                throw new BusinessException(EmBusinessError.UNKNOW_ERROR);
            }
            // ????????????, ???????????????
            accountService.returned(AccountDetail.create(detail.getId(), detail.getCredit() + detail.getFee(),  AccountDetailType.RETURN));
        }
    }

}
