package com.qijianguo.game.model.enums;

import lombok.Getter;

/**
 * @author qijianguo
 */

@Getter
public enum NotificationType {

    REWARD(1),

    WITHDRAW(2);

    private Integer type;

    NotificationType(Integer type) {
        this.type = type;
    }
}
