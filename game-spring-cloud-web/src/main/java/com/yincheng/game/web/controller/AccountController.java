package com.yincheng.game.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.anno.CurrentUser;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.enums.Role;
import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.AccountDetail;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.*;
import com.yincheng.game.service.AccountDetailService;
import com.yincheng.game.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qijianguo
 */
@RestController
@RequestMapping("/account")
@Api(tags = "账户管理")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountDetailService accountDetailService;

    @ApiOperation(value = "账户余额", response = AccountResp.class)
    @PostMapping
    @Authentication
    public Result account(@ApiIgnore @CurrentUser User user) {
        Account account = accountService.get(user.getId());
        return Result.success(new AccountResp(account));
    }

    @ApiOperation(value = "账户明细", response = AccountDetailResp.class)
    @PostMapping("/detail")
    @Authentication
    public Result list(@ApiIgnore @CurrentUser User user, AccountDetailReq req) {
        IPage<AccountDetail> page = accountDetailService.page(user, req);
        List<AccountDetail> records = page.getRecords();
        IPage result = new Page();
        BeanUtils.copyProperties(page, result);
        result.setRecords(convertFromPo(records));
        return Result.success(result);
    }

    @ApiOperation(value = "首次注册赠送积分", response = AccountResp.class)
    @PostMapping("/reward")
    @Authentication
    public Result giving(@ApiIgnore @CurrentUser User user) {
        AccountDetail detail = AccountDetail.valueOf(user.getId(), 100000,  AccountDetailType.GIFT);
        Account account = accountService.giving(detail);
        return Result.success(new AccountResp(account));
    }

    @ApiOperation(value = "充值配置选项", response = AccountResp.class)
    @GetMapping("/prepaid/config")
    @Authentication
    public Result prepaidConfig(@ApiIgnore @CurrentUser User user) {
        boolean exists = accountDetailService.exists(user.getId(), AccountDetailType.PREPAID);
        String tag = !exists ? "+20%" : "";
        List<AccountPrepaid2Resp> resps = new ArrayList<>();
        resps.add(new AccountPrepaid2Resp(20000, tag));
        resps.add(new AccountPrepaid2Resp(50000, tag));
        resps.add(new AccountPrepaid2Resp(100000, tag));
        resps.add(new AccountPrepaid2Resp(300000, tag));
        resps.add(new AccountPrepaid2Resp(500000, tag));
        resps.add(new AccountPrepaid2Resp(1000000, tag));
        return Result.success(resps);
    }


    @ApiOperation(value = "申请提现", response = AccountResp.class)
    @PostMapping("/withdraw")
    @Authentication
    public Result withdraw(@ApiIgnore @CurrentUser User user, AccountWithdrawReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        AccountDetail detail = AccountDetail.valueOf(user.getId(), req.getCredit(), AccountDetailType.WITHDRAW);
        Account account = accountService.withdraw(detail);
        return Result.success(new AccountResp(account));
    }

    @ApiOperation(value = "审核提现申请(ADMIN)")
    @GetMapping("/withdraw/review")
    @Authentication(roles = Role.ADMIN)
    public Result reviewWithdraw(@ApiIgnore @CurrentUser User user, AccountWithdrawReviewReq req) {
        accountService.review(req);
        return Result.success();
    }

    private List<AccountDetailResp> convertFromPo(List<AccountDetail> poList) {
        List<AccountDetailResp> respList = new ArrayList<>();
        poList.forEach(po -> {
            AccountDetailResp resp = new AccountDetailResp();
            BeanUtils.copyProperties(po, resp);
            respList.add(resp);
        });
        return respList;
    }

}
