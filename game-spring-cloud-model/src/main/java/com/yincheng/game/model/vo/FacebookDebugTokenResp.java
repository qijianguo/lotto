package com.yincheng.game.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Facebook的token验证地址（%7C为encode的 '|'符号）：
 * https://graph.facebook.com/debug_token?access_token={Your AppId}%7C{Your AppSecret}&input_token=XXX
 * @author qijianguo
 */
@NoArgsConstructor
@Data
public class FacebookDebugTokenResp {


    /**
     * data : {"app_id":"1165322047236078","type":"USER","application":"Easylotre","data_access_expires_at":1624254729,"expires_at":1621584817,"is_valid":true,"issued_at":1616400817,"scopes":["public_profile"],"user_id":"102105701972120"}
     */

    private DataBean data;

    @NoArgsConstructor
    @Data
    public static class DataBean {
        /**
         * app_id : 1165322047236078
         * type : USER
         * application : Easylotre
         * data_access_expires_at : 1624254729
         * expires_at : 1621584817
         * is_valid : true
         * issued_at : 1616400817
         * scopes : ["public_profile"]
         * user_id : 102105701972120
         */

        private String app_id;
        private String type;
        private String application;
        private int data_access_expires_at;
        private int expires_at;
        private boolean is_valid;
        private int issued_at;
        private String user_id;
        private List<String> scopes;
    }
}
