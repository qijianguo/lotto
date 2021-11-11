package com.qijianguo.game.model.enums;

/**
 * @author qijianguo
 */
public class Destination {

    private static final String TOPIC = "topic";
    private static final String QUEUE = "queue";

    private static final String TOPIC_PREFIX = "/topic/";

    private static final String QUEUE_PREFIX = "/queue/";

    private static String destination(String type, String address) {
        switch (type) {
            case QUEUE:
                return QUEUE_PREFIX + address;
            case TOPIC:
                return TOPIC_PREFIX + address;
        }
        return null;
    }

    /**
     * 广播 TOPIC：开奖结果
     * @param game 游戏名
     * @return
     */
    public static String resultTopic(String game) {
        return destination(TOPIC, game);
    }

    /**
     * 一对一 QUEUE：账户余额
     * @return
     */
    public static String accountQueue() {
        return destination(QUEUE, "account");
    }

    /**
     * 广播 TOPIC：开奖结果
     * @param game 游戏名
     * @return
     */
    public static String rewardTopic(String game) {
        return destination(TOPIC, game + "/reward");
    }

}
