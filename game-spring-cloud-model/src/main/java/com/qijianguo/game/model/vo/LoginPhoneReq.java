package com.qijianguo.game.model.vo;

import com.qijianguo.game.model.anno.CacheParam;
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
    @CacheParam
    private String phone;

    @ApiModelProperty(value = "CODE", required = true, dataType = "String")
    private String code;

    public boolean validate() {
        return phone != null && code != null;
    }

}
