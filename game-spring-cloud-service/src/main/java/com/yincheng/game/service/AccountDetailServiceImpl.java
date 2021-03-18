package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.dao.mapper.AccountDetailMapper;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.AccountDetail;
import org.springframework.stereotype.Service;

/**
 * @author qijianguo
 */
@Service
public class AccountDetailServiceImpl extends ServiceImpl<AccountDetailMapper, AccountDetail> implements AccountDetailService{

    @Override
    public AccountDetail prepaid(AccountDetail detail) {
        return null;
    }

    @Override
    public AccountDetail speed(Account account, Integer credit) {
        AccountDetail detail = new AccountDetail();
        detail.speed(account.getUserId(), credit, AccountDetailType.SPEED);
        detail.setBalance(account.getBalance());
        boolean save = save(detail);
        if (!save) {
            throw new BusinessException(EmBusinessError.UNKNOW_ERROR);
        }
        return detail;
    }

    @Override
    public AccountDetail cashOut(AccountDetail detail) {
        return null;
    }
}
