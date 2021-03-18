package com.yincheng.game.service.login;

import com.yincheng.game.model.po.User;
import com.yincheng.game.model.po.UserAuth;
import com.yincheng.game.model.vo.LoginFbReq;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qijianguo
 */
@Component
public class FacebookLogin implements Login<LoginFbReq> {

    @Override
    public User execute(LoginFbReq params) {

        return null;
    }

    @Override
    public void verify(LoginFbReq req) {
        System.out.println("Facebook verify");
    }

    @Override
    public List<UserAuth> register(LoginFbReq params) {

        System.out.println("Facebook verify");
        return null;
    }

    @Override
    public User signIn(List<UserAuth> auths) {
        System.out.println("Facebook signIn");
        return null;
    }
}
