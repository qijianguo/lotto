package com.yincheng.game.configuration;

import com.yincheng.game.interceptor.AuthChannelInterceptor;
import com.yincheng.game.interceptor.HttpWebSocketHandlerDecoratorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * @author qijianguo
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Autowired
    private HttpWebSocketHandlerDecoratorFactory httpWebSocketHandlerDecoratorFactory;

    /**
     * 配置 WebSocket 进入点，及开启使用 SockJS，这些配置主要用配置连接端点，用于 WebSocket 连接
     *
     * @param registry STOMP 端点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/mydlq")
                // 设置允许跨域
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * 配置消息代理选项
     *
     * @param registry 消息代理注册配置
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 设置一个或者多个代理前缀，在 Controller 类中的方法里面发生的消息，会首先转发到代理从而发送到对应广播或者队列中。
        registry.enableSimpleBroker("/topic", "/queue");
        // 配置客户端发送请求消息的一个或多个前缀，该前缀会筛选消息目标转发到 Controller 类中注解对应的方法里
        registry.setApplicationDestinationPrefixes("/app");
        // 服务端通知特定用户客户端的前缀，可以不设置，默认为user
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(new HttpWebSocketHandlerDecoratorFactory());
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new AuthChannelInterceptor());
    }

    /*@Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        logger.info("configureClientInboundChannel....");
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                *//*StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor == null) {
                    return null;
                }
                // 首次连接
                String token = (String) Optional.ofNullable(accessor.getHeader("token")).orElse("");
                if (StompCommand.CONNECT.equals(accessor.getCommand()) && !StringUtils.isEmpty(token)) {
                    // 认证, 获取用户信息，并分配token
                    if (pass(token)) {
                        Principal principal = () -> token;
                        accessor.setUser(principal);
                        return message;
                    }
                }*//*

                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor == null) {
                    return null;
                }
                // 首次连接
                String simpSessionId =  message.getHeaders().get("simpSessionId").toString();
                if (StompCommand.CONNECT.equals(accessor.getCommand()) && !StringUtils.isEmpty(simpSessionId)) {
                    // 认证, 获取用户信息，并分配token
                    if (pass(simpSessionId)) {
                        Principal principal = () -> simpSessionId;
                        accessor.setUser(principal);
                        WebSocketSupport.sessionSet.add(simpSessionId);
                        return message;
                    }
                }
                logger.info("configureClientInboundChannel.preSend");
                return message;
            }
        });
    }*/

    private boolean pass(String token) {
        return true;
    }
}
