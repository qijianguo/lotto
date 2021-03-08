package com.yincheng.game.enums;

/**
 * @author qijianguo
 */
public class Destination {

    private static final String TOPIC = "/topic/";

    private static final String QUEUE = "/queue/";

    public static String topic(String gameType) {
        return TOPIC + gameType;
    }

    public static String queue(String address) {
        return QUEUE + address;
    }

    public static String gameResult(String game) {
        return topic(game);
    }

    public static String account() {
        return queue("account");
    }

}
