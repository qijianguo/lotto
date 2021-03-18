package com.yincheng.game.model.enums;

import lombok.Getter;

/**
 * @author qijianguo
 */
@Getter
public enum WsNoticeType {

    TOPIC_3D_RESULT("/topic/3d"),
    TOPIC_4D_RESULT("/topic/4d"),
    TOPIC_5D_RESULT("/topic/5d"),

    QUEUE_ACCOUNT("/queue/account"),

    ;

    private String destination;

    WsNoticeType(String destination) {
        this.destination = destination;
    }
}
