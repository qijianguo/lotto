package com.qijianguo.game.model.vo;

import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class SpBaseReq {

    /** 签名 */
    private String sign;

    /** 合作商id， Sinopay分配的 */
    private String partnerId = "";
}
