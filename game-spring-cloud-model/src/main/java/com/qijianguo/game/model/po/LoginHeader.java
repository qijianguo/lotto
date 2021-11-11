package com.qijianguo.game.model.po;

import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class LoginHeader {

    private String device;
    private String deviceId;
    private String token;
    private String currTime;
    private String nonce;
    private String sign;

}
