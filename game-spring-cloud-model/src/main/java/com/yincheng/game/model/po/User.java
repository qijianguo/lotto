package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yincheng.game.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

/**
 * @author qijianguo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class User implements Serializable {
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
    /** 角色 */
    private String roles;
    /** 注册时间 */
    private Date createTime;
    /** 最近时间 */
    private Date updateTime;
    /** 登录令牌 */
    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private List<UserAuth> auths = new ArrayList<>();

    public static User valueOf(String nickName, String avatar) {
        User user = new User();
        user.setNickName(nickName);
        user.setAvatar(avatar);
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getUpdateTime());
        user.setStatus(0);
        user.setRoles(Role.USER.name());
        user.setRealName("");
        user.setGender(-1);
        return user;
    }
}