package com.yincheng.game.service.login;

import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.po.UserAuth;
import com.yincheng.game.model.vo.LoginReq;
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
public class PhoneLogin implements Login {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private TokenService tokenService;

    public static final String TYPE = "PHONE";

    @Override
    public User execute(LoginReq req) {
        verify(req);
        User user = register(req);
        updateToken(user);
        return user;
    }

    @Override
    public void verify(LoginReq req) {
        // TODO 验证码是否正确
        String phone = req.getPhone();
        String code = req.getCode();
        if (phone.equals("17521226604") && !code.equals("1234")) {
            throw new BusinessException(EmBusinessError.INVALID_PHONE_CODE);
        }
    }

    @Override
    public User register(LoginReq req) {
        List<UserAuth> userAuths = userAuthService.getUserAuths(req.getType(), req.getPhone());
        // 查看是否已经注册过，没有的话注册一下
        User user;
        if (!CollectionUtils.isEmpty(userAuths)) {
            // 已经注册过了
            user = userService.getById(userAuths.get(0).getUserId());
            user.setAuths(userAuths);
            userService.updateById(user);
        } else {
            user = new User();
            user.init(req.getPhone(), "http://xxx.jpg");
            boolean save = userService.save(user);
            if (save) {
                UserAuth userAuth = new UserAuth(user.getId(), req.getType(), req.getPhone(), "");
                userAuthService.save(userAuth);
                user.getAuths().add(userAuth);
            }
        }
        return user;
    }

    @Override
    public User register(String unionId, String nickName, String cover) {
        List<UserAuth> userAuths = userAuthService.getUserAuths(TYPE, unionId);
        // 查看是否已经注册过，没有的话注册一下
        User user;
        if (!CollectionUtils.isEmpty(userAuths)) {
            // 已经注册过了
            user = userService.getById(userAuths.get(0).getUserId());
            user.setAuths(userAuths);
            userService.updateById(user);
        } else {
            user = new User();
            user.init(unionId, cover);
            boolean save = userService.save(user);
            if (save) {
                UserAuth userAuth = new UserAuth(user.getId(), TYPE, unionId, "");
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
