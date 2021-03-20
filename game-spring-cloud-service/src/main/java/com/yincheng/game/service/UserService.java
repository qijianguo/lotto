package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.LoginReq;

/**
 * @author qijianguo
 */
public interface UserService extends IService<User> {

    /**
     * 注册或登录
     * @param req
     * @return
     */
    User login(LoginReq req);



}
