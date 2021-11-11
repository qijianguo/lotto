package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qijianguo.game.model.po.SinopayDetail;
import com.qijianguo.game.model.vo.AccountPrepaidConformReq;
import com.qijianguo.game.model.vo.AccountPrepaidReq;
import com.qijianguo.game.model.vo.SpPreOrderResp;
import com.qijianguo.game.model.vo.SpWithdrawReq;
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
     * @param userId 用户ID
     * @param accountDetailId 记录ID
     * @param req
     */
    void disburse(Integer userId, Integer accountDetailId, SpWithdrawReq req);

    //void queryOrder();

}
