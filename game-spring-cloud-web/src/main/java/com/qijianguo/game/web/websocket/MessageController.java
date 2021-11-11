package com.qijianguo.game.web.websocket;

import com.qijianguo.game.model.vo.MessageQueueBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author qijianguo
 */
@Controller
public class MessageController {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

//    /** 广播发送消息，将消息发送到指定的目标地址 */
//    @MessageMapping("/test")
//    public void sendTopicMessage(MessageBody messageBody) {
//        // 将消息发送到 WebSocket 配置类中配置的代理中（/topic）进行消息转发
//        simpMessageSendingOperations.convertAndSend(messageBody.getDestination(), messageBody);
//    }
//
//    @MessageMapping("/queue")
//    public void sendToUser(Principal principal, MessageQueueBody messageQueueBody) {
//        messageQueueBody.setFrom(principal.getName());
//        simpMessageSendingOperations.convertAndSendToUser(messageQueueBody.getTargetUser(), messageQueueBody.getDestination(), messageQueueBody);
//    }

    @MessageMapping("/test")
    public void sendUserMessage(Principal principal, MessageQueueBody messageBody) {
        // 设置发送消息的用户
        messageBody.setFrom(principal.getName());
        // 调用 STOMP 代理进行消息转发
        simpMessageSendingOperations.convertAndSendToUser(messageBody.getTargetUser(), messageBody.getDestination(), messageBody);
    }

}