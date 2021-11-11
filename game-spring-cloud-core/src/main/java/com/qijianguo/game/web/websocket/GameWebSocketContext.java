package com.qijianguo.game.web.websocket;

import com.alibaba.fastjson.JSON;
import com.qijianguo.game.model.vo.RecentTaskResp;
import com.qijianguo.game.context.GameContext;
import com.qijianguo.game.common.serializer.FastJsonSerializer;

/**
 * WebSocket通信中的上下文
 * @author qijianguo
 */
public class GameWebSocketContext<T> extends GameContext {

    private static final long serialVersionUID = -1205530535069540245L;

    public void sendResult(RecentTaskResp resp) {
        WebSocketEvent<RecentTaskResp> task = new WebSocketEvent<>("result", resp);
        noticeAll(task);
    }

    public <T>void noticeAll(WebSocketEvent<T> event) {
        String message = JSON.toJSONString(event, FastJsonSerializer.serializeConfig);
        WebSocketSupport.sendAll(message);
    }

}
