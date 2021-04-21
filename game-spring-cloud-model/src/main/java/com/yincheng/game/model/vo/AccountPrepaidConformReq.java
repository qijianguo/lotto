package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author qijianguo
 */
@Data
@ApiModel("充值成功确认")
public class AccountPrepaidConformReq {

    @ApiModelProperty(value = "第三方充值订单号")
    private String orderId;

    public boolean validate() {
        return !StringUtils.isEmpty(orderId);
    }

}
