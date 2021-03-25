package com.yincheng.game.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * WebSocket用户上、下线监听
 * @author qijianguo
 */
@Configuration
public class HttpWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {

    private static final Logger logger = LoggerFactory.getLogger(HttpWebSocketHandlerDecoratorFactory.class);

    @Override
    public WebSocketHandler decorate(WebSocketHandler webSocketHandler) {
        return new WebSocketHandlerDecorator(webSocketHandler) {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                Principal principal = session.getPrincipal();
                if (principal != null) {
                    logger.info("用户:{} 上线了", principal.getName());
                    super.afterConnectionEstablished(session);
                }
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                Principal principal = session.getPrincipal();
                if (principal != null) {
                    logger.info("用户:{} 下线了", principal.getName());
                    super.afterConnectionClosed(session, closeStatus);
                }
            }

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                Principal principal = session.getPrincipal();
                if (principal != null && message != null) {
                    logger.info("用户:{} 发送消息：{}", principal.getName(), message.getPayload());
                    super.handleMessage(session, message);
                }
            }
        };
    }
    private String getToken(Message message){
        Map headers = (Map) message.getHeaders().get("nativeHeaders");
        if (headers !=null && headers.containsKey("token")){
            List token = (List)headers.get("token");
            return String.valueOf(token.get(0));
        }
        return null;
    }
}