package com.yincheng.game.web.websocket;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSupport {

    private static final ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap();

    public static final Set<String> sessionSet = new HashSet<>();

    /**
     * 存储Session
     * @param session
     */
    public static boolean storageSession(Session session) {
        // TODO 鉴权
        sessionPools.put(session.getId(), session);
        return true;
    }

    /**
     * 移除Session
     * @param session
     */
    public static void remove(Session session) {
        sessionPools.remove(session.getId());
    }

    /**
     * 检查Session是否存在
     * @param session
     * @return
     */
    public static boolean exists(Session session) {
        return sessionPools.contains(session.getId());
    }

    /**
     * 发送消息
     * @param session
     * @param message
     */
    public static void sendOne(Session session, String message) {
        try {
            if (session.isOpen()) {
                if (session.isOpen()) {
                    synchronized (session) {
                        session.getBasicRemote().sendText(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送给所有人
     * @param message
     */
    public static void sendAll(String message) {
        sessionPools.forEach((id, session) -> {
            sendOne(session, message);
        });
    }

}
