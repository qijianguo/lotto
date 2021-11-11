package com.qijianguo.game.web.websocket;

import com.qijianguo.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;

/**
 * WebSocket通信编辑服务
 * @author qijianguo
 */
//@ServerEndpoint("/ws")
//@Component
public class WebSocketEditorServer {

    private static Logger logger = LoggerFactory.getLogger(WebSocketEditorServer.class);

    public static Game game;

    @OnOpen
    public void onOpen(Session session) {
        // TODO 鉴权
        boolean success = WebSocketSupport.storageSession(session);
        if (!success) {
            return;
        }
        logger.info("websocket.open. SessionId:{}", session.getId());
        // 发送当前的游戏状态
        WebSocketSupport.sendOne(session, "welcome to join in us!");
    }

    @OnError
    public void opError(Session session, Throwable throwable) {
        // 发生错误
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        boolean check = WebSocketSupport.exists(session);
        if (!check) {
            logger.info("校验不通过");
            return;
        }
        // 来自客户端的消息
        logger.info("websocket.message:{}, SessionId:{}", message, session.getId());
        if ("join".equalsIgnoreCase(message)) {
            //

        }

    }

    @OnClose
    public void onClose(Session session) {
        logger.info("websocket.close. SessionId:{}", session.getId());
        WebSocketSupport.remove(session);
    }

}
