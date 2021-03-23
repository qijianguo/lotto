package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class LoginFacebookReq {
    @ApiModelProperty(value = "Fb授权Token", required = true, dataType = "String")
    private String accessToken;
    @ApiModelProperty(value = "Fb用户ID", required = true, dataType = "String")
    private String fbUid;
    @ApiModelProperty(value = "昵称", required = true, dataType = "String")
    private String nickName;
    @ApiModelProperty(value = "头像", required = true, dataType = "String")
    private String avatar;

    public boolean validate() {
        return accessToken != null && fbUid != null && nickName != null && avatar != null;
    }

}
