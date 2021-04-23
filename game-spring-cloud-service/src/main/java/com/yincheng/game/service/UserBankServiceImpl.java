package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.dao.mapper.UserBankMapper;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.po.UserBank;
import com.yincheng.game.model.vo.BankReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank> implements UserBankService{

    @Autowired
    private UserBankMapper userBankMapper;

    @Override
    public UserBank save(User user, BankReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        UserBank bank;
        if (req.getId() != null) {
            bank = userBankMapper.selectById(req.getId());
            if (bank == null) {
                throw new BusinessException(EmBusinessError.WITHDRAW_BANK_NOT_FOUND);
            }
            lambdaUpdate().eq(UserBank::getId, req.getId())
                    .set(req.getBank() != null, UserBank::getBank, req.getBank())
                    .set(!StringUtils.isEmpty(req.getBranch()), UserBank::getBranch, req.getBranch())
                    .set(!StringUtils.isEmpty(req.getCardNo()), UserBank::getCardNo, req.getCardNo())
                    .set(!StringUtils.isEmpty(req.getRealName()), UserBank::getRealName, req.getRealName())
                    .update();

        } else {
            bank = UserBank.create(user, req);
            userBankMapper.insert(bank);
        }

        return bank;
    }

    @Override
    public List<UserBank> list(User user) {
        return lambdaQuery()
                .eq(UserBank::getUserId, user.getId())
                .list();
    }

    @Override
    public UserBank getLatestAddOne(Integer userId) {
        IPage<UserBank> page = lambdaQuery()
                .eq(UserBank::getUserId, userId)
                .orderByDesc(UserBank::getId)
                .page(new Page<>(1, 1, false));
        List<UserBank> records = page.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            return records.get(0);
        }
        return null;
    }
}
