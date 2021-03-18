package com.yincheng.game.model.po;

import lombok.Data;

import java.util.Date;

/**
 * 用户授权
 * @author qijianguo
 */
@Data
public class UserAuth {

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

}
