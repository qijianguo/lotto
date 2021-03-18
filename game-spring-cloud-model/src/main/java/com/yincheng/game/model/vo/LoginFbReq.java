package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@ApiModel("Facebook登录")
@Data
public class LoginFbReq {

    @ApiModelProperty(value = "CODE", required = true, dataType = "String")
    private String code;

}
