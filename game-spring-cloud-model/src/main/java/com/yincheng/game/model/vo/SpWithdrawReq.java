package com.yincheng.game.model.vo;

import com.yincheng.game.common.util.TimeUtils;
import com.yincheng.game.model.po.UserBank;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author qijianguo
 * 提现请求参数
 */
@Data
public class SpWithdrawReq extends SpBaseReq {

    /** 你方订单id */
    private String outOrderId;
    /** 付款数量，元为单位 */
    private BigDecimal money;
    /** 收款银行，如：中国银行 */
    private String bank;
    /** 支行 */
    private String branch;
    /** 收款人姓名 */
    private String owner;
    /** 收款账号 */
    private String account;

    public static SpWithdrawReq create(UserBank bank, String money) {
        SpWithdrawReq req = new SpWithdrawReq();
        req.setOutOrderId("sp" + TimeUtils.formatDate(new Date(), TimeUtils.TIME_STAMP_PATTERN) + RandomUtils.nextInt(100, 999));
        req.setMoney(new BigDecimal(money));
        req.setBank(bank.getBank());
        req.setBranch(bank.getBranch());
        req.setOwner(bank.getRealName());
        req.setAccount(bank.getCardNo());
        return req;
    }

    public boolean validate() {
        return outOrderId != null && money != null && bank != null && branch != null && owner != null && account != null;
    }
}
