package com.yincheng.game.web.controller;

import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.anno.CurrentUser;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.AccountDetail;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.*;
import com.yincheng.game.service.SinopayDetailService;
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
    public Result prepaid(@ApiIgnore @CurrentUser User user, AccountPrepaidReq req) {
        req.setUserId(user.getId());
        SpPreOrderResp order = sinopayDetailService.createOrder(req);
        return Result.success(new AccountPrepaidResp(order));
    }

    @ApiOperation(value = "订单通知", response = Map.class)
    @PostMapping("/notify")
    public Map orderNotify(HttpServletRequest request) {
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
    public Result orderConform(AccountPrepaidConformReq req) {
        sinopayDetailService.conform(req);
        return Result.success();
    }

    /*@ApiOperation(value = "申请提现", response = AccountResp.class)
    @PostMapping("/withdraw")
    @Authentication
    public Result withdraw(@ApiIgnore @CurrentUser User user, AccountWithdrawReq req) {
        Account withdraw = sinopayDetailService.withdraw(user, req);
        return Result.success(new AccountResp(withdraw));
    }*/
}
