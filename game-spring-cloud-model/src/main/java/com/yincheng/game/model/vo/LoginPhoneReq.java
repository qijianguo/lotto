package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@ApiModel("手机号登录")
@Data
public class LoginPhoneReq {

    @ApiModelProperty(value = "手机号", required = true, dataType = "String")
    private String phone;

    @ApiModelProperty(value = "CODE", required = true, dataType = "String")
    private String code;


}
