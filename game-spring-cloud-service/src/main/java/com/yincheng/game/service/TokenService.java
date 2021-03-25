package com.yincheng.game.service;

import com.yincheng.game.model.po.User;

/**
 * @author qijianguo
 */
public interface TokenService {

    String create(User user);

    void remove(String token);

    boolean verify(User user);

    String verify(String token);

}
