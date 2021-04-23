package com.yincheng.game.model;

/**
 * @author qijianguo
 */
public class RedisKeys {

    public static String user(Integer userId) {
        return String.format("user:%s", userId);
    }

    public static String userAccount(Integer userId) {
        return "user_account:" + userId;
    }

    /** 中奖通知 */
    public static String noticeReward() {
        return "notice:reward";
    }
    /** 提现通知 */
    public static String noticeWithdraw() {
        return "notice:withdraw";
    }
}
