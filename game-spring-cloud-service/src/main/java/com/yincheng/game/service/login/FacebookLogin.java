package com.yincheng.game.service.login;

import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.po.UserAuth;
import com.yincheng.game.model.vo.FacebookUserResp;
import com.yincheng.game.model.vo.LoginReq;
import com.yincheng.game.service.FacebookService;
import com.yincheng.game.service.TokenService;
import com.yincheng.game.service.UserAuthService;
import com.yincheng.game.service.UserService;
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
            user = new User();
            user.init(userInfo.getName(), userInfo.getPicture());
            boolean save = userService.save(user);
            if (save) {
                UserAuth userAuth = new UserAuth(user.getId(), req.getType(), userInfo.getId(), "");
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
            user = new User();
            user.init(nickName, cover);
            boolean save = userService.save(user);
            if (save) {
                UserAuth userAuth = new UserAuth(user.getId(), "facebook", unionId, "");
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
