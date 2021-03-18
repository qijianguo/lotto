package com.yincheng.game.service.login;

import com.yincheng.game.model.po.User;
import com.yincheng.game.model.po.UserAuth;

import java.util.List;

/**
 * @author qijianguo
 */
public interface Login<T> {

    User execute(T req);

    /**
     * 参数校验
     * @param req
     */
    void verify(T req);

    /**
     * 获取注册信息
     * @param params
     * @return
     */
    List<UserAuth> register(T params);

    /**
     * 登录
     */
    User signIn(List<UserAuth> auths);

}
