package com.qijianguo.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@ApiModel("登录")
@Data
public class LoginReq {

    @ApiModelProperty(value = "登录方式：phone、facebook", required = true, dataType = "String")
    private String type;
    @ApiModelProperty(value = "手机号，只有登录方式为phone时传递", dataType = "String")
    private String phone;
    @ApiModelProperty(value = "CODE", required = true, dataType = "String")
    private String code;

    public boolean validate() {
        return type != null && code != null;
    }

}
