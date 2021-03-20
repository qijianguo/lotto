package com.yincheng.game.service.login;

import com.yincheng.game.model.po.User;
import com.yincheng.game.model.po.UserAuth;
import com.yincheng.game.model.vo.LoginReq;

import java.util.List;

/**
 * @author qijianguo
 */
public interface Login {

    User execute(LoginReq req);

    /**
     * 参数校验
     * @param req
     */
    List<UserAuth> verify(LoginReq req);

    /**
     * 获取注册信息
     * @param req
     * @return
     */
    User register(LoginReq req);

    void updateToken(User user);

}
