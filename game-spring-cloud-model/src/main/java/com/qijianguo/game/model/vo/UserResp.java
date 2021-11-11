package com.qijianguo.game.model.vo;

import com.qijianguo.game.model.po.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel("用户信息")
public class UserResp {

    @ApiModelProperty(value = "登录令牌", required = true, dataType = "String")
    private String token;
    @ApiModelProperty(value = "昵称", required = true, dataType = "String")
    private String nickName;
    @ApiModelProperty(value = "头像", required = true, dataType = "String")
    private String avatar;

    public UserResp(User user) {
        if (user != null) {
            this.token = user.getToken();
            this.nickName = user.getNickName();
            this.avatar = user.getAvatar();
        }
    }
}
