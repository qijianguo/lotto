package com.qijianguo.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel("预充值响应")
public class AccountPrepaidResp {

    @ApiModelProperty(value = "第三方支付地址", required = true, dataType = "String", example = "http://xxx.html")
    private String url;
    @ApiModelProperty(value = "第三方订单ID", required = true, dataType = "String", example = "adc1234efd")
    private String orderId;

    public AccountPrepaidResp(SpPreOrderResp orderResp) {
        if (orderResp != null) {
            this.url = orderResp.getUrl();
            this.orderId = orderResp.getOrderId();
        }
    }
}
