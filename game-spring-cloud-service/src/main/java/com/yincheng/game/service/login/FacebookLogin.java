package com.yincheng.game.service.login;

import com.yincheng.game.model.po.User;
import com.yincheng.game.model.po.UserAuth;
import com.yincheng.game.model.vo.LoginFbReq;
import com.yincheng.game.model.vo.LoginReq;
import com.yincheng.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qijianguo
 */
@Component
public class FacebookLogin implements Login {

    @Override
    public User execute(LoginReq req) {
        return null;
    }

    @Override
    public List<UserAuth> verify(LoginReq req) {
        return null;
    }

    @Override
    public User register(LoginReq req) {
        return null;
    }

    @Override
    public void updateToken(User user) {

    }
}
