package com.qijianguo.game.service;

import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.vo.UserPrincipal;

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
    User simpleVerify(String token);

    /**
     * 获取用户认证信息
     * @param token
     * @return
     */
    UserPrincipal getPrincipal(String token);

}
