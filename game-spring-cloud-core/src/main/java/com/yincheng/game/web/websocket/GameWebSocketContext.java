package com.yincheng.game.web.websocket;

import com.alibaba.fastjson.JSON;
import com.yincheng.game.context.GameContext;
import com.yincheng.game.model.vo.TaskWsResp;
import com.yincheng.game.common.serializer.FastJsonSerializer;

/**
 * WebSocket通信中的上下文
 * @author qijianguo
 */
public class GameWebSocketContext<T> extends GameContext {

    private static final long serialVersionUID = -1205530535069540245L;

    public void sendResult(TaskWsResp resp) {
        WebSocketEvent<TaskWsResp> task = new WebSocketEvent<>("result", resp);
        noticeAll(task);
    }

    public <T>void noticeAll(WebSocketEvent<T> event) {
        String message = JSON.toJSONString(event, FastJsonSerializer.serializeConfig);
        WebSocketSupport.sendAll(message);
    }

}
