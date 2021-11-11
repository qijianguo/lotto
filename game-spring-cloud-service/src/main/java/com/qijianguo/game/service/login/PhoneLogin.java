package com.qijianguo.game.service.login;

import com.qijianguo.game.common.exception.BusinessException;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.po.UserAuth;
import com.qijianguo.game.model.vo.LoginReq;
import com.qijianguo.game.service.TokenService;
import com.qijianguo.game.service.UserAuthService;
import com.qijianguo.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
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
            user = User.valueOf(req.getPhone(), "http://xxx.jpg");
            boolean save = userService.save(user);
            if (save) {
                UserAuth userAuth = UserAuth.valueOf(user.getId(), req.getType(), req.getPhone(), "");
                userAuthService.save(userAuth);
                user.getAuths().add(userAuth);
            }
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
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
            user = User.valueOf(unionId, cover);
            boolean save = userService.save(user);
            if (save) {
                UserAuth userAuth = UserAuth.valueOf(user.getId(), TYPE, unionId, "");
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
