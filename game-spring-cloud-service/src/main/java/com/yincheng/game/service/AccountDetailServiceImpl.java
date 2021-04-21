package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.dao.mapper.AccountDetailMapper;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.po.AccountDetail;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.AccountDetailReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author qijianguo
 */
@Service
public class AccountDetailServiceImpl extends ServiceImpl<AccountDetailMapper, AccountDetail> implements AccountDetailService{

    @Autowired
    private AccountDetailMapper accountDetailMapper;

    @Override
    public IPage<AccountDetail> page(User user, AccountDetailReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        IPage<AccountDetail> page = new Page<>(req.getCurrent(), req.getSize(), req.isSearchCount());
        return lambdaQuery()
                .eq(AccountDetail::getUserId, user.getId())
                .eq(AccountDetail::getType, req.getType())
                .page(page);
    }

    @Override
    public List<AccountDetail> top(AccountDetailType detailType) {
        if (detailType == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        Page<AccountDetail> pageReq = new Page<>();
        pageReq.setSearchCount(false);
        pageReq.setDesc("create_time");
        IPage page = lambdaQuery()
                .eq(AccountDetail::getType, 5)
                .page(pageReq);
        return page.getRecords();
    }

    @Override
    public boolean exists(Integer userId, AccountDetailType type) {
        //LambdaQueryChainWrapper<AccountDetail> exists = lambdaQuery().exists(String.format("select * from t_account_detail where user_id = %d and type = %d", user.getId(), type.getType()));
        int exists = Optional.ofNullable(accountDetailMapper.exists(userId, type.getType())).orElse(0);
        return exists == 1;
    }

    @Override
    public IPage<AccountDetail> top(AccountDetailReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        IPage<AccountDetail> page = new Page<>();
        BeanUtils.copyProperties(req, page);
        return lambdaQuery()
                .eq(AccountDetail::getType, req.getType())
                .page(page);
    }
}
