package com.yincheng.game.service;

import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.enums.LoginMode;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.FacebookDebugTokenResp;
import com.yincheng.game.model.vo.LoginFacebookReq;
import com.yincheng.game.model.vo.LoginPhoneReq;
import com.yincheng.game.model.vo.LoginReq;
import com.yincheng.game.service.login.FacebookLogin;
import com.yincheng.game.service.login.PhoneLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qijianguo
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private PhoneLogin phoneLogin;
    @Autowired
    private FacebookLogin facebookLogin;
    @Autowired
    private FacebookService facebookService;

    @Override
    public User login(LoginReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        LoginMode loginMode = LoginMode.valueOf(req.getType().toUpperCase());
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

    @Override
    public User login(LoginPhoneReq req) {
        return null;
    }

    @Override
    public User login(LoginFacebookReq req) {
        /*if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }*/
        // 验证AccessToken
        facebookService.verifyAccessToken(req.getAccessToken(), req.getFbUid());
        User register = facebookLogin.register(req.getFbUid(), req.getNickName(), req.getAvatar());
        facebookLogin.updateToken(register);
        return register;
    }



}
