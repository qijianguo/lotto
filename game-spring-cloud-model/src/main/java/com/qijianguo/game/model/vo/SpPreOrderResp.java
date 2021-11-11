package com.qijianguo.game.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qijianguo
 */
@NoArgsConstructor
@Data
public class SpPreOrderResp {
    /**
     * result : ok
     * url : https://checkout.xendit.co/web/607e6007f6530840bed72f2f
     * pay_type : UNIONPAYH5
     * providerOrderId : 607e6007f6530840bed72f2f
     * outOrderId : sp20210420130052943212
     * orderId : 607e600656363933e13b7411
     * sign : 2327814e2de77f6639f6372caba27d66
     */

     /** 错误，错误可能是字符串，对象，数字等，一般会有可以阅读部分。如果有err，以下参数全部不存在 */
    private String err;
    /** 你方系统的订单 */
    private String outOrderId;
    /** Sinopay的订单id */
    private String orderId;
    /** 提供方的订单id,请注意有的供应商不会给出这个参数 */
    private String providerOrderId;
    /** 支付用地址, 这里有两种可能
     * 1. 您可以直接跳往这个地址进行具体支付
     * 2. 如果是WECAHATPAYH5，ALIPAYH5，您应该将这个地址显示成二维码提供给用户
     * 3. 具体的处理方式请咨询对接小伙伴
     */
    private String url;

    private String result;

    private String pay_type;

    private String sign;

}
