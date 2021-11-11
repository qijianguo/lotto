package com.qijianguo.game.model.vo;

import com.qijianguo.game.common.util.TimeUtils;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author qijianguo
 */
@Data
public class SpPreOrderReq extends SpBaseReq {
    /** 充值结果回调地址，以http或https开头，如 http://mydomain.com/pay/result */
    private String cb_url;
    /** 充值币种，取决于您接入的通道不同，目前支持 CAD，USD, CNY，IDR，THB */
    private String currency;
    /** 充值数量，以元为单位，如 1.25，请注意这里直接以当地货币计价，不是人民币 */
    private BigDecimal money;
    /** 国家/地区，具体参数询问对接小伙伴 */
    private String country;
    /** 你方系统的订单号，必须是唯一的 */
    private String outOrderId;
    /** 充值界面的回跳地址，一般就显示充值成功 */
    private String return_url;
    /** 当前时间，毫秒单位 */
    private Long time;
    /** 你方系统中充值用户的id */
    private String userId;
    /** 可以是 WECHATPAYH5，ALIPAYH5，UNIONPAYH5，USERSELECT, 不同通道支持不一样，可以不填 */
    private String type;

    public static SpPreOrderReq init(Integer userId, Integer money) {
        SpPreOrderReq req = new SpPreOrderReq();
        req.setCb_url("http://");
        req.setCurrency("IDR");
        req.setMoney(BigDecimal.valueOf(money));
        req.setCountry("IDN");
        req.setOutOrderId("sp" + TimeUtils.formatDate(new Date(), TimeUtils.TIME_STAMP_PATTERN) + RandomUtils.nextInt(100, 999));
        req.setReturn_url("http://");
        req.setTime(System.currentTimeMillis());
        req.setUserId(String.valueOf(userId));
        req.setType(null);
        return req;
    }
}
