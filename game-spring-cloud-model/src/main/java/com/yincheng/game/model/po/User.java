package com.yincheng.game.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qijianguo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    /** 邀请码 */
    private String uid;
    /** 昵称 */
    private String name;
    /** 真实姓名 */
    private String realName;
    /** 性别 */
    private Integer gender;
    /** 头像 */
    private String avatar;
    /** 状态 */
    private Integer status;
    /** 登录令牌 */
    private String token;

    private List<UserAuth> auths;

}