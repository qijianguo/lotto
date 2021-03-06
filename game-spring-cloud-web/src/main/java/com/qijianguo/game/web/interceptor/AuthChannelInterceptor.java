package com.qijianguo.game.web.interceptor;

import com.qijianguo.game.model.Constants;
import com.qijianguo.game.model.vo.UserPrincipal;
import com.qijianguo.game.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * WebSocket 通道拦截器（这里模拟两个测试 Token 方便测试，不做具体 Token 鉴权实现）
 *
 * @author mydlq
 */
@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private TokenService tokenService;

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
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 如果token不为空，说明用户已登录， username = id
            if (!StringUtils.isEmpty(token)) {
                UserPrincipal tokenUser = tokenService.getPrincipal(token);
                if (tokenUser != null) {
                    //accessor.setUser(() -> String.valueOf(tokenUser.getId()));
                    accessor.setUser(tokenUser);
                }
            }
            // 如果token为空，则用户未登录，username = sessionId
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
        if (headers !=null && headers.containsKey(Constants.TOKEN)){
            List token = (List)headers.get(Constants.TOKEN);
            return String.valueOf(token.get(0));
        }
        return null;
    }

}