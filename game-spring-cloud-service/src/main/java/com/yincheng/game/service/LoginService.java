package com.yincheng.game.service;

import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.LoginFacebookReq;
import com.yincheng.game.model.vo.LoginPhoneReq;
import com.yincheng.game.model.vo.LoginReq;

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
