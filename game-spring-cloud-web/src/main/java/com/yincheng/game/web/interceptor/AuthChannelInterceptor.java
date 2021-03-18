package com.yincheng.game.web.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 通道拦截器（这里模拟两个测试 Token 方便测试，不做具体 Token 鉴权实现）
 *
 * @author mydlq
 */
@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    private static Map<String, String> userTokenMap = new ConcurrentHashMap<>();

    static {
        userTokenMap.put("123456-1", "user1");
        userTokenMap.put("123456-2", "user2");
    }

    /**
     * 从 Header 中获取 Token 进行验证，根据不同的 Token 区别用户
     *
     * @param message 消息对象
     * @param channel 通道对象
     * @return 验证后的用户信息
     */
    @Override
    public Message preSend(Message message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        String token = getToken(message);
        if (!StringUtils.isEmpty(token) && accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            if (auth(token)) {
                accessor.setUser(() -> userTokenMap.get(token));
            }
        }
        return message;
    }

    /**
     * 从 Header 中获取 TOKEN
     *
     * @param message 消息对象
     * @return TOKEN
     */
    private String getToken(Message message){
        Map headers = (Map) message.getHeaders().get("nativeHeaders");
        if (headers !=null && headers.containsKey("token")){
            List token = (List)headers.get("token");
            return String.valueOf(token.get(0));
        }
        return null;
    }

    private boolean auth(String token) {
        // TODO 用户权限校验


        return true;
    }

}