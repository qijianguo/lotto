package com.yincheng.game.service;

import com.yincheng.game.model.po.User;

/**
 * @author qijianguo
 */
public interface TokenService {

    /**
     * 新建/修改 TOKEN
     * @param user 用户信息
     * @return
     */
    String create(User user);

    /**
     * 移除TOKEN
     * @param token
     */
    void remove(String token);

    /**
     * 校验TOKEN
     * @param token
     * @return
     */
    User verify(String token);

    /**
     * 简单算法校验TOKEN，并获取userId
     * @param token
     * @return
     */
    Integer simpleVerify(String token);

}
