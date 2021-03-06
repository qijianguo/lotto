package com.qijianguo.game.model.enums;


/**
 * @author qijianguo
 */
public enum AccountDetailType {

    /** 充值 */
    PREPAID(1, 0, 0),
    /** 消费 */
    SPEED(2, 1, 1),
    /** 提现 */
    WITHDRAW(3, 0, 0),
    /** 赠送 */
    GIFT(4, 1, 1),
    /** 收入 */
    REWARD(5, 1, 1),
    /** 退回 */
    RETURN(6, 1, 1),
    ;

    /**
     * 明细类型
     */
    private Integer type;

    /**
     * 是否确认
     */
    private Integer defConfirm;

    /***
     * 是否成功
     */
    private Integer success;

    AccountDetailType(Integer type, Integer defConfirm, Integer success) {
        this.type = type;
        this.defConfirm = defConfirm;
        this.success = success;
    }

    public Integer getType() {
        return type;
    }

    public Integer getDefConfirm() {
        return defConfirm;
    }

    public Integer getSuccess() {
        return success;
    }
}
