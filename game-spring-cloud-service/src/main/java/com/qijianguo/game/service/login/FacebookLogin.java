package com.qijianguo.game.service.login;

import com.qijianguo.game.common.exception.BusinessException;
import com.qijianguo.game.common.exception.EmBusinessError;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.po.UserAuth;
import com.qijianguo.game.model.vo.FacebookUserResp;
import com.qijianguo.game.model.vo.LoginReq;
import com.qijianguo.game.service.FacebookService;
import com.qijianguo.game.service.TokenService;
import com.qijianguo.game.service.UserAuthService;
import com.qijianguo.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author qijianguo
 */
@Component
public class FacebookLogin implements Login {

    @Autowired
    private FacebookService facebookService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Override
    public User execute(LoginReq req) {
        verify(req);
        User user = register(req);
        updateToken(user);
        return user;
    }

    @Override
    public void verify(LoginReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
    }

    @Override
    public User register(LoginReq req) {
        String accessToken = facebookService.getAccessToken(req.getCode());
        FacebookUserResp userInfo = facebookService.getUserInfo(accessToken);
        List<UserAuth> userAuths = userAuthService.getUserAuths(req.getType(), userInfo.getId());
        User user;
        if (!CollectionUtils.isEmpty(userAuths)) {
            // 已经注册过了
            user = userService.getById(userAuths.get(0).getUserId());
            user.setAuths(userAuths);
            userService.updateById(user);
        } else {
            user = User.valueOf(userInfo.getName(), userInfo.getPicture());
            boolean save = userService.save(user);
            if (save) {
                UserAuth userAuth = UserAuth.valueOf(user.getId(), req.getType(), userInfo.getId(), "");
                userAuthService.save(userAuth);
                user.getAuths().add(userAuth);
            }
        }
        return user;
    }

    @Override
    public User register(String unionId, String nickName, String cover) {
        List<UserAuth> userAuths = userAuthService.getUserAuths("facebook", unionId);
        User user;
        if (!CollectionUtils.isEmpty(userAuths)) {
            // 已经注册过了
            user = userService.getById(userAuths.get(0).getUserId());
            user.setAuths(userAuths);
            userService.updateById(user);
        } else {
            user = User.valueOf(nickName, cover);
            boolean save = userService.save(user);
            if (save) {
                UserAuth userAuth = UserAuth.valueOf(user.getId(), "facebook", unionId, "");
                userAuthService.save(userAuth);
                user.getAuths().add(userAuth);
            }
        }
        return user;
    }

    @Override
    public void updateToken(User user) {
        tokenService.create(user);
    }
}
