package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
@TableName("t_account")
@NoArgsConstructor
public class Account {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 用户ID */
    private Integer userId;
    /** 余额 */
    private Integer balance;
    /** 状态：0新用户，1老用户 */
    private Integer status;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

    public Account(Integer userId) {
        this.userId = userId;
        this.balance = 0;
        this.status = 0;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }
}
