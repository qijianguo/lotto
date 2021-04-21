package com.yincheng.game.service;

import com.alibaba.fastjson.JSON;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.common.util.ApiUtils;
import com.yincheng.game.common.util.HttpUtils;
import com.yincheng.game.common.util.MD5Utils;
import com.yincheng.game.model.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author qijianguo
 * Sinopay 四方接口
 * https://api.sinopay.asia/#/docs
 */
@Service
public class SinopayService {

    private static final Logger logger = LoggerFactory.getLogger(SinopayService.class);

    private static final String PARTNER_ID = "607cf716debcb5ed5defa777";

    private static final String KEY = "2e5f56156ce26c5785d2078a1e4c49fcf8020907";

    private static final String PRE_ORDER = "http://api.sinopay.asia/forecore/order";
    private static final String QUERY_ORDER = "http://api.sinopay.asia/forecore/queryOrder";
    private static final String DISBURSE = "http://api.sinopay.asia/forecore/disburse";
    
    private static final String PARAM_SIGN = "sign";

    /**
     * 下单
     * @param req 参数
     * @return 响应
     */
    public SpPreOrderResp preOrder(SpPreOrderReq req) {
        Map<String, String> params = null;
        String result = null;
        try {
            req.setPartnerId(PARTNER_ID);
            params = ApiUtils.jsonToMap(JSON.toJSONString(req));
            String sign = sign(params);
            params.put(PARAM_SIGN, sign);
            result = HttpUtils.getInstance().executeGetWithJson(PRE_ORDER, params);
            SpPreOrderResp spPreOrderResp = JSON.parseObject(result, SpPreOrderResp.class);
            if (spPreOrderResp != null && StringUtils.isEmpty(spPreOrderResp.getErr())) {
                return spPreOrderResp;
            } else {
                logger.warn("preOrder => params: 「{}」, result:「{}」", params, result);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.warn("queryOrder => params: 「{}」, result:「{}」", params, result);
        throw new BusinessException(EmBusinessError.SINOPAY_ORDER_FIELD);
    }

    /**
     * 查询订单
     * @param req 参数
     */
    public SpQueryOrderResp queryOrder(SpQueryOrderReq req) {
        Map<String, String> params = null;
        String result = null;
        try {
            req.setPartnerId(PARTNER_ID);
            params = ApiUtils.jsonToMap(JSON.toJSONString(req));
            String sign = sign(params);
            params.put(PARAM_SIGN, sign);
            result = HttpUtils.getInstance().executeGetWithJson(QUERY_ORDER, params);
            SpQueryOrderResp spQueryOrderResp = JSON.parseObject(result, SpQueryOrderResp.class);
            if (spQueryOrderResp != null && StringUtils.isEmpty(spQueryOrderResp.getErr())) {
                return spQueryOrderResp;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.warn("queryOrder => params: 「{}」, result:「{}」", params, result);
        throw new BusinessException(EmBusinessError.SINOPAY_ORDER_FIELD);
    }

    /**
     * 代付
     * @param req
     * @return
     */
    public SpWithdrawResp disburse(SpWithdrawReq req) {
        Map<String, String> params = null;
        String result = null;
        try {
            req.setPartnerId(PARTNER_ID);
            params = ApiUtils.jsonToMap(JSON.toJSONString(req));
            String sign = sign(params);
            params.put(PARAM_SIGN, sign);
            result = HttpUtils.getInstance().executeGetWithJson(DISBURSE, params);
            SpWithdrawResp spQueryOrderResp = JSON.parseObject(result, SpWithdrawResp.class);
            if (spQueryOrderResp != null && StringUtils.isEmpty(spQueryOrderResp.getErr())) {
                return spQueryOrderResp;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.warn("disburse => params: 「{}」, result:「{}」", params, result);
        throw new BusinessException(EmBusinessError.SINOPAY_DISBURSE_FIELD);
    }

    public String sign(Map<String, String> req) throws UnsupportedEncodingException {
        Map<String, String> map = ApiUtils.jsonToMap(JSON.toJSONString(req));
        Assert.notNull(map, "签名参数Map为空！");
        String paramsStr = ApiUtils.concatSignString(map);
        return MD5Utils.getMD5(KEY + paramsStr);
    }

}
