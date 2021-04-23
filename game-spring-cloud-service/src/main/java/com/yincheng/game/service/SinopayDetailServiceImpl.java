package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.dao.mapper.SinopayDetailMapper;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.po.*;
import com.yincheng.game.model.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author qijianguo
 */
@Service
public class SinopayDetailServiceImpl extends ServiceImpl<SinopayDetailMapper, SinopayDetail> implements SinopayDetailService {

    private static final Logger logger = LoggerFactory.getLogger(SinopayDetailServiceImpl.class);

    private static final String SUCCESS = "SUCCESS";
    private static final String REFUNDING = "REFUNDING";
    private static final String CLOSE = "CLOSE";

    @Autowired
    private SinopayService sinopayService;
    @Autowired
    private SinopayDetailMapper sinopayDetailMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountDetailService accountDetailService;
    @Autowired
    private UserBankService userBankService;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public SpPreOrderResp createOrder(AccountPrepaidReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        // 判断用户是否充值过，如果没有充值过，则奖励
        boolean exists = accountDetailService.exists(req.getUserId(), AccountDetailType.PREPAID);
        Integer credit;
        if (!exists) {
            credit = (int) (req.getCredit() * 1.2d);
        } else {
            credit = req.getCredit();
        }
        SpPreOrderReq orderReq = SpPreOrderReq.init(req.getUserId(), credit);
        SpPreOrderResp orderResp = sinopayService.preOrder(orderReq);

        SinopayDetail entity = SinopayDetail.init(req.getUserId(), orderReq, orderResp);
        sinopayDetailMapper.insert(entity);

        return orderResp;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Map orderNotify(Map<String, String> params) {
        String sign;
        try {
            sign = sinopayService.sign(params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(EmBusinessError.SINOPAY_SIGN_ERROR);
        }
        Map<String, String> reback = new HashMap<>(1);
        if (!Objects.equals(params.get("sign"), sign)) {
            reback.put("err", "Sign error.");
            return reback;
        }
        // 订单是否已经成功
        SinopayDetail detail = sinopayDetailMapper.selectByOutOrderId(params.get("outOrderId"));
        if (detail == null) {
            logger.warn("Order not found. params:{}", params);
            reback.put("err", "Order not found.");
            return reback;
        }
        // 订单是否已处理
        if (Objects.equals(detail.getStatus(), SUCCESS)) {
            reback.put("success", "notify success.");
            return reback;
        }
        if (detail.getMoney().doubleValue() != Double.parseDouble(params.get("money"))) {
            logger.warn("Order prepaid money not match. params:{}", params);
            reback.put("err", "Order prepaid money not match.");
            return reback;
        }
        // 手动查询一下订单状态
        SpQueryOrderReq queryOrder = new SpQueryOrderReq(detail.getOutOrderId());
        SpQueryOrderResp spQueryOrderResp = sinopayService.queryOrder(queryOrder);
        if (!Objects.equals(spQueryOrderResp.getXendit_ret().getStatus(), SUCCESS)) {
            logger.warn("Order status no finished. params:{}", params);
            reback.put("err", "Order status no finished.");
            return reback;
        }

        // 目前只支持印尼盾，其他不支持
        if (!"IDR".equalsIgnoreCase(detail.getCurrency())) {
            logger.warn("Order prepaid money not match. params:{}", params);
            reback.put("err", "Order prepaid currency not match.");
        }
        AccountDetail accountDetail = AccountDetail.create(detail.getUserId(), detail.getMoney().intValue(),  AccountDetailType.PREPAID);
        accountService.prepaid(accountDetail);

        sinopayDetailMapper.update(
                accountDetail.getId(),
                params.get("orderId"),
                detail.getMoney(),
                params.get("status"),
                detail.getId()
        );

        reback.put("success", "notify success.");
        return reback;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void conform(AccountPrepaidConformReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        SinopayDetail detail = sinopayDetailMapper.selectByOrderId(req.getOrderId());
        if (detail == null) {
            throw new BusinessException(EmBusinessError.ACCOUNT_PREPAID_ORDER_NOT_FOUND);
        }
        if (Objects.equals(detail.getStatus(), SUCCESS)) {
            return;
        }
        SpQueryOrderReq queryOrder = new SpQueryOrderReq(detail.getOutOrderId());
        SpQueryOrderResp spQueryOrderResp = sinopayService.queryOrder(queryOrder);
        if (!Objects.equals(spQueryOrderResp.getXendit_ret().getStatus(), SUCCESS)) {
            logger.warn("Order no pay. params:{}", spQueryOrderResp);
            throw new BusinessException(EmBusinessError.ACCOUNT_ORDER_NO_PAY);
        }
        // 目前只支持印尼盾，其他不支持
        if (!"IDR".equalsIgnoreCase(spQueryOrderResp.getCurrency())) {
            logger.warn("Order prepaid money not match. params:{}", spQueryOrderResp);
            throw new BusinessException(EmBusinessError.SINOPAY_CURRENCY_NOT_SUPPORT);
        }

        AccountDetail accountDetail = AccountDetail.create(detail.getUserId(), detail.getMoney().intValue(),  AccountDetailType.PREPAID);
        accountService.prepaid(accountDetail);

        sinopayDetailMapper.update(
                accountDetail.getId(),
                spQueryOrderResp.getOrderId(),
                detail.getMoney(),
                spQueryOrderResp.getXendit_ret().getStatus(),
                detail.getId()
        );
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void disburse(Integer userId, Integer accountDetailId, SpWithdrawReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        // 发起代付申请
        SpWithdrawResp disburse = sinopayService.disburse(req);
        // 保存代扣记录
        SinopayDetail entity = SinopayDetail.init(userId, accountDetailId, req, disburse);
        sinopayDetailMapper.insert(entity);
    }
}
