package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yincheng.game.common.util.TimeUtils;
import com.yincheng.game.model.vo.SpPreOrderReq;
import com.yincheng.game.model.vo.SpPreOrderResp;
import com.yincheng.game.model.vo.SpWithdrawReq;
import com.yincheng.game.model.vo.SpWithdrawResp;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 四方充值记录
 * @author qijianguo
 */
@TableName("t_sinopay_detail")
@Data
public class SinopayDetail {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String tradeType;

    private String currency;

    private BigDecimal money;

    private String country;

    private String outOrderId;

    private Date time;

    private String type;

    private String orderId;

    private String providerOrderId;

    private BigDecimal received;

    private String status;

    private String bank;
    /** 支行 */
    private String branch;
    /** 收款人姓名 */
    private String owner;
    /** 收款账号 */
    private String account;

    private Date createTime;

    private Date updateTime;


    public static SinopayDetail init(int userId, SpPreOrderReq req, SpPreOrderResp orderResp) {
        // 请求参数
        SinopayDetail detail = new SinopayDetail();
        detail.setUserId(userId);
        detail.setCurrency(req.getCurrency());
        detail.setMoney(req.getMoney());
        detail.setCountry(req.getCountry());
        detail.setOutOrderId(req.getOutOrderId());
        detail.setTime(TimeUtils.long2DateStr(req.getTime()));
        detail.setType(req.getType());
        // 响应参数
        detail.setOrderId(orderResp.getOrderId());
        detail.setProviderOrderId(orderResp.getProviderOrderId());

        detail.setCreateTime(new Date());
        detail.setUpdateTime(detail.getCreateTime());
        return detail;
    }

    public static SinopayDetail init(int userId, SpWithdrawReq req, SpWithdrawResp orderResp) {
        // 请求参数
        SinopayDetail detail = new SinopayDetail();
        detail.setUserId(userId);
        detail.setMoney(req.getMoney());
        detail.setOutOrderId(req.getOutOrderId());
        detail.setBank(req.getBank());
        detail.setBranch(req.getBranch());
        detail.setOwner(req.getOwner());
        detail.setAccount(req.getAccount());
        // 响应参数

        detail.setCreateTime(new Date());
        detail.setUpdateTime(detail.getCreateTime());
        return detail;
    }


}
