package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.dao.mapper.UserMapper;
import com.yincheng.game.model.enums.LoginMode;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.LoginReq;
import com.yincheng.game.service.login.FacebookLogin;
import com.yincheng.game.service.login.Login;
import com.yincheng.game.service.login.LoginOperate;
import com.yincheng.game.service.login.PhoneLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理
 * @author qijianguo
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PhoneLogin phoneLogin;
    @Autowired
    private FacebookLogin facebookLogin;

    @Override
    public User login(LoginReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        LoginMode loginMode = LoginMode.valueOf(req.getType());
        User user = null;
        switch (loginMode) {
            case PHONE:
                user = phoneLogin.execute(req);
                break;
            case FACEBOOK:
                user = facebookLogin.execute(req);
                break;
            default:
                break;
        }
        return user;
    }
}
