package com.qijianguo.game.service.login;

import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.vo.LoginReq;

/**
 * @author qijianguo
 */
public interface Login {

    User execute(LoginReq req);

    /**
     * 参数校验
     * @param req
     */
    void verify(LoginReq req);

    /**
     * 获取注册信息
     * @param req
     * @return
     */
    User register(LoginReq req);

    User register(String unionId, String nickName, String cover);

    void updateToken(User user);

}
