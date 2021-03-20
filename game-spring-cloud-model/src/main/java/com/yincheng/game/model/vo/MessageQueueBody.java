package com.yincheng.game.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 点对点消息推送
 * @author qijianguo
 */
@Data
@NoArgsConstructor
public class MessageQueueBody extends MessageBody {

    /** 发送消息的用户 */
    private String from;
    /** 目标用户（告知 STOMP 代理转发到哪个用户） */
    private String targetUser;

    public MessageQueueBody(String targetUser, String destination, String content) {
        super(destination, content);
        this.targetUser = targetUser;
    }
}
