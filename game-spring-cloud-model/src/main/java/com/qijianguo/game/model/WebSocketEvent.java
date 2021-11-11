package com.qijianguo.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * WebSocket 事件统一返回
 * @author qijianguo
 */
@Data
@NoArgsConstructor
public class WebSocketEvent<T> {

    private String eventType;

    private Date timestamp;

    private T message;

    public WebSocketEvent(String eventType, T message) {
        this.eventType = eventType;
        this.message = message;
    }

    public WebSocketEvent(String eventType, Date timestamp, T message) {
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.message = message;
    }
}
