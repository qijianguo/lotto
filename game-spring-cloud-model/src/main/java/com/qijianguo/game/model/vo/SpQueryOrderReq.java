package com.qijianguo.game.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qijianguo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpQueryOrderReq extends SpBaseReq {

    /** 我方订单ID */
    private String outOrderId;
}
