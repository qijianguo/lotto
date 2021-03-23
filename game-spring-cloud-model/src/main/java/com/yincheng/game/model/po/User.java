package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author qijianguo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 昵称 */
    private String nickName;
    /** 真实姓名 */
    private String realName;
    /** 性别 */
    private Integer gender;
    /** 头像 */
    private String avatar;
    /** 状态 */
    private Integer status;
    private String role;
    /** 注册时间 */
    private Date createTime;
    /** 最近时间 */
    private Date updateTime;
    /** 邀请码 */
    @TableField(exist = false)
    private String uid;
    /** 设备信息 */
    @TableField(exist = false)
    private String device;
    /** 登录令牌 */
    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private List<UserAuth> auths;

    public void init(String nickName, String avatar) {
        this.nickName = nickName;
        this.avatar = avatar;
        this.updateTime = this.createTime = new Date();
        this.status = 0;
        this.realName = "";
        this.gender = -1;
    }

}