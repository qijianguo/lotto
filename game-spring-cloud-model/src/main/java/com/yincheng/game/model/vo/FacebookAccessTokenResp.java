package com.yincheng.game.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qijianguo
 */
@NoArgsConstructor
@Data
public class FacebookAccessTokenResp {

    /**
     * access_token :
     * token_type :
     * expires_in : 13312312
     */
    private String access_token;
    private String token_type;
    private int expires_in;
}
