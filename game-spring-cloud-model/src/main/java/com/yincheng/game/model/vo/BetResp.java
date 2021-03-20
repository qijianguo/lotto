package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel("下注响应")
public class BetResp {

    @ApiModelProperty(value = "余额", required = true, dataType = "Integer")
    private Integer balance;

}
