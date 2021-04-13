package com.yincheng.game.configuration;

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

    /*@Autowired
    public void setGame(Game game) {
        WebSocketEditorServer.game = game;
    }*/
}
