package com.qijianguo.game.service;

import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.vo.LoginFacebookReq;
import com.qijianguo.game.model.vo.LoginPhoneReq;
import com.qijianguo.game.model.vo.LoginReq;

/**
 * @author qijianguo
 */
public interface LoginService {

    /**
     * 注册或登录
     * @param req
     * @return
     */
    User login(LoginReq req);

    /**
     * 手机号登录
     * @param req
     * @return
     */
    User login(LoginPhoneReq req);

    /**
     * facebook登录
     * @param req
     * @return
     */
    User login(LoginFacebookReq req);

}
