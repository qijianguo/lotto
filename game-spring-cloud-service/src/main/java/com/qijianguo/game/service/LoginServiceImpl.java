package com.qijianguo.game.service;

import com.qijianguo.game.common.exception.BusinessException;
import com.qijianguo.game.common.exception.EmBusinessError;
import com.qijianguo.game.common.util.RegexUtils;
import com.qijianguo.game.model.enums.LoginMode;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.service.login.FacebookLogin;
import com.qijianguo.game.service.login.PhoneLogin;
import com.qijianguo.game.model.vo.LoginFacebookReq;
import com.qijianguo.game.model.vo.LoginPhoneReq;
import com.qijianguo.game.model.vo.LoginReq;
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
        if (!req.validate() && !RegexUtils.isMobile2(req.getPhone())) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        User register = phoneLogin.register(req.getPhone(), RegexUtils.replaceWithStar(req.getPhone()), "http://1.jpg");
        phoneLogin.updateToken(register);
        return register;
    }

    @Override
    public User login(LoginFacebookReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        // 验证AccessToken
        boolean verify = facebookService.verifyAccessToken(req.getAccessToken(), req.getFbUid());
        if (!verify) {
            throw new BusinessException(EmBusinessError.INVALID_FACEBOOK_ACC_TOKEN);
        }
        User register = facebookLogin.register(req.getFbUid(), req.getNickName(), req.getAvatar());
        facebookLogin.updateToken(register);
        return register;
    }



}
