package com.yincheng.game.service.login;

import com.yincheng.game.model.po.User;
import com.yincheng.game.model.po.UserAuth;
import com.yincheng.game.model.vo.LoginPhoneReq;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qijianguo
 */
@Component
public class PhoneLogin implements Login<LoginPhoneReq> {

    @Override
    public User execute(LoginPhoneReq req) {
        verify(req);
        List<UserAuth> auths = register(req);
        return signIn(auths);
    }

    @Override
    public void verify(LoginPhoneReq req) {
        System.out.println("Phone verify");
    }

    @Override
    public List<UserAuth> register(LoginPhoneReq params) {
        System.out.println("Phone register");
        return null;
    }

    @Override
    public User signIn(List<UserAuth> auths) {
        System.out.println("Phone signIn");
        return null;
    }

}
