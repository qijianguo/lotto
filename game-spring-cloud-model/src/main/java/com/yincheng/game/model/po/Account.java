package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.enums.AccountDetailType;
import lombok.Data;

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

    public void increase(AccountDetail detail) {
        // 是否是新用户奖励
        if (AccountDetailType.GIFT.getType().equals(detail.getType())) {
            if (this.status != 0) {
                throw new BusinessException(EmBusinessError.REWARD_REPEATED_ERROR);
            }
            this.status = 1;
        }
        this.balance += detail.getCredit();
        this.updateTime = new Date();
        detail.setBalance(this.balance);
    }

    public void decrease(AccountDetail detail) {
        if (detail.getCredit() > this.balance) {
            throw new BusinessException(EmBusinessError.ACCOUNT_INSUFFICIENT_BALANCE);
        }
        this.balance -= detail.getCredit();
        this.updateTime = new Date();
        detail.setBalance(this.balance);
    }
}
