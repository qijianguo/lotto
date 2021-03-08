package com.yincheng.game.service;

import com.alibaba.fastjson.JSON;
import com.yincheng.game.enums.Destination;
import com.yincheng.game.model.po.GameFlow;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.MessageBody;
import com.yincheng.game.model.vo.MessageQueueBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import com.yincheng.game.serializer.FastJsonSerializer;

/**
 * @author qijianguo
 */
@Service
public class WebSocketService<T> {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 广播订阅此消息的用户
     * @param destination
     * @param data
     */
    public void send(String destination, Object data) {
        send(new MessageBody(destination, JSON.toJSONString(data, FastJsonSerializer.serializeConfig)));
    }

    /**
     * 广播订阅此消息的用户
     * @param destination
     * @param data
     */
    public void send(String user, String destination, Object data) {
        send(new MessageQueueBody(user, destination, JSON.toJSONString(data, FastJsonSerializer.serializeConfig)));
    }

    private void send(MessageBody messageBody) {
        simpMessageSendingOperations.convertAndSend(messageBody.getDestination(), messageBody);
    }

    private void send(MessageQueueBody messageQueueBody) {
        simpMessageSendingOperations.convertAndSendToUser(messageQueueBody.getTargetUser(), messageQueueBody.getDestination(), messageQueueBody);
    }
}
