package com.yincheng.game.model.enums;

/**
 * @author qijianguo
 */
public class Destination {

    private static final String TOPIC = "/topic/";

    private static final String QUEUE = "/queue/";

    private static String gameTopic(String gameType) {
        return TOPIC + gameType;
    }

    private static String queue(String address) {
        return QUEUE + address;
    }

    public static String gameResult(String game) {
        return gameTopic(game);
    }

    public static String account() {
        return queue("account");
    }

    public static String rewardTopic(String gameType) {
        return TOPIC + gameType + "/reward";
    }

}
