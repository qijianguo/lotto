package com.yincheng.game.model;

/**
 * @author qijianguo
 */
public class RedisKeys {

    public static String user(Integer userId) {
        return String.format("user:%s", userId);
    }

    public static String notice(String type) {
        return String.format("notice:%s", type);
    }
}
