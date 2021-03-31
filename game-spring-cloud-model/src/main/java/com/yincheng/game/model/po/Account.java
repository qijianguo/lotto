package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField(exist = false)
    private Integer reward;

    public static Account init(Integer userId) {
        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(0);
        account.setReward(0);
        account.setStatus(0);
        account.setCreateTime(new Date());
        account.setUpdateTime(account.getCreateTime());
        return account;
    }
}
