package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.dao.mapper.AccountDetailMapper;
import com.yincheng.game.model.po.AccountDetail;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.AccountDetailReq;
import org.springframework.stereotype.Service;

/**
 * @author qijianguo
 */
@Service
public class AccountDetailServiceImpl extends ServiceImpl<AccountDetailMapper, AccountDetail> implements AccountDetailService{

    @Override
    public IPage<AccountDetail> page(User user, AccountDetailReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        return lambdaQuery()
                .eq(AccountDetail::getUserId, user.getId())
                .eq(AccountDetail::getType, req.getType())
                .page(req);
    }
}
