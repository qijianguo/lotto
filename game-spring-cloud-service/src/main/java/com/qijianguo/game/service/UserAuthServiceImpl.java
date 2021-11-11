package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qijianguo.game.dao.mapper.UserAuthMapper;
import com.qijianguo.game.model.po.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qijianguo
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public UserAuth getByUnionId(String unionId) {
        return lambdaQuery().eq(UserAuth::getUnionId, unionId).one();
    }

    @Override
    public List<UserAuth> getUserAuths(String mode, String unionId) {
        return userAuthMapper.getUserAuths(mode, unionId);
    }

    @Override
    public List<UserAuth> getByUser(Integer userId) {
        return lambdaQuery().eq(UserAuth::getUserId, userId).list();
    }
}
