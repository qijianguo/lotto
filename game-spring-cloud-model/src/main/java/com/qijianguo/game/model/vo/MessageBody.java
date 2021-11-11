package com.qijianguo.game.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qijianguo
 */
@Data
@NoArgsConstructor
public class MessageBody {

    private String destination;
    
    private String content;

    public MessageBody(String destination, String content) {
        this.destination = destination;
        this.content = content;
    }
}
