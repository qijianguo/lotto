package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户授权
 * @author qijianguo
 */
@Data
@TableName("t_user_auth")
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 用户ID */
    private Integer userId;
    /** 注册方式 */
    private String mode;
    /** */
    private String unionId;
    /** */
    private String openId;

    private Date createTime;

    private Date updateTime;

    public UserAuth(Integer userId, String mode, String unionId, String openId) {
        this.userId = userId;
        this.mode = mode;
        this.unionId = unionId;
        this.openId = openId;
        this.updateTime = this.createTime = new Date();
    }
}
