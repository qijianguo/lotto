package com.yincheng.game.configuration;

import com.yincheng.game.Game;
import com.yincheng.game.web.websocket.WebSocketEditorServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author qijianguo
 */
@Configuration
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter endpointExporter(){
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setGame(Game game) {
        WebSocketEditorServer.game = game;
    }
}
