package com.qijianguo.game.web.controller;

import com.qijianguo.game.model.Result;
import com.qijianguo.game.model.anno.Authentication;
import com.qijianguo.game.model.anno.CacheLock;
import com.qijianguo.game.model.anno.CacheParam;
import com.qijianguo.game.model.anno.CurrentUser;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.vo.*;
import com.qijianguo.game.service.SinopayDetailService;
import com.yincheng.game.model.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qijianguo
 */
@RestController
@RequestMapping("/sinopay")
@Api(tags = "四方充值")
public class SinopayDetailController {

    @Autowired
    private SinopayDetailService sinopayDetailService;

    @ApiOperation(value = "充值积分", response = AccountResp.class)
    @PostMapping("/prepaid")
    @Authentication
    @CacheLock(prefix = "sinopay_prepaid")
    public Result prepaid(@ApiIgnore @CurrentUser User user, AccountPrepaidReq req) {
        req.setUserId(user.getId());
        SpPreOrderResp order = sinopayDetailService.createOrder(req);
        return Result.success(new AccountPrepaidResp(order));
    }

    @ApiOperation(value = "订单通知", response = Map.class)
    @PostMapping("/notify")
    @CacheLock(prefix = "sinopay_notify")
    public Map orderNotify(@CacheParam HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, String> params = new HashMap<>(6);
        if (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            params.put(name, request.getParameter(name));
        }
        return sinopayDetailService.orderNotify(params);
    }

    @ApiOperation(value = "订单确认", response = AccountResp.class)
    @PostMapping("/conform")
    @Authentication
    @CacheLock(prefix = "sinopay_conform")
    public Result orderConform(AccountPrepaidConformReq req) {
        sinopayDetailService.conform(req);
        return Result.success();
    }

}
