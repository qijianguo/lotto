package com.yincheng.game.model.vo;

import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class MessageBody {

    private String destination;
    
    private String content;

    public MessageBody(String destination, String content) {
        this.destination = destination;
        this.content = content;
    }
}
