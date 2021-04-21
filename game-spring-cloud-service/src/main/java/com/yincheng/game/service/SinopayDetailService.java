package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.SinopayDetail;
import com.yincheng.game.model.vo.*;

import java.util.Map;

/**
 * @author qijianguo
 */
public interface SinopayDetailService extends IService<SinopayDetail> {

    /**
     * 生成订单
     * @param req
     * @return
     */
    SpPreOrderResp createOrder(AccountPrepaidReq req);

    /**
     * 通知
     * @param params
     */
    Map orderNotify(Map<String, String> params);

    void conform(AccountPrepaidConformReq req);

    /**
     * 代付
     * @param req
     */
    void disburse(Integer userId, SpWithdrawReq req);

    //void queryOrder();

}
