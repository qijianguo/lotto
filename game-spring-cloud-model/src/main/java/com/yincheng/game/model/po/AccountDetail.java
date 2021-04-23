package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yincheng.game.model.enums.AccountDetailType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
@TableName("t_account_detail")
@NoArgsConstructor
public class AccountDetail {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 用户ID */
    private Integer userId;
    /** 类型：1充值，2消费，3提现 4赠送，5中奖，6退回 -1 其他 */
    private Integer type;
    /** 余额 */
    private Integer balance;
    /** 充值/消费积分 */
    private Integer credit;
    /** 手续费 */
    private Integer fee;
    /** 状态确认：充值/消费默认为1，提现默认为0 */
    private Integer confirm;
    /**
     * 是否成功：1成功，0失败或待处理
     * 赠送/消费/退回默认success=1 充值/提现默认为0
     * 提现转出条件必须为confirm=1 & success=0才允许
     */
    private Integer success;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

    public Integer getCost() {
        return credit - fee;
    }

    public void setDetailType(AccountDetailType speed) {
        this.type = speed.getType();
        this.confirm = speed.getDefConfirm();
        this.success = speed.getSuccess();
    }

    public static AccountDetail create(Integer userId, Integer credit, AccountDetailType detailType) {
        return createWithFee(userId, credit, 0, detailType);
    }

    public static AccountDetail createWithFee(Integer userId, Integer credit, Integer fee, AccountDetailType detailType) {
        AccountDetail detail = new AccountDetail();
        detail.setUserId(userId);
        detail.setType(detailType.getType());
        detail.setConfirm(detailType.getDefConfirm());
        detail.setSuccess(detailType.getSuccess());
        detail.setCredit(credit);
        detail.setFee(fee);
        detail.setCreateTime(new Date());
        detail.setUpdateTime(detail.getCreateTime());
        return detail;
    }

    public boolean validate() {
        return userId != null && type != null && balance != null && credit != null && confirm != null && success != null;
    }

}
