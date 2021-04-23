package com.yincheng.game.web.controller;

import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.anno.CacheLock;
import com.yincheng.game.model.anno.CurrentUser;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.po.UserBank;
import com.yincheng.game.model.vo.AccountResp;
import com.yincheng.game.model.vo.BankReq;
import com.yincheng.game.model.vo.BankResp;
import com.yincheng.game.service.UserBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author qijianguo
 */
@RestController
@RequestMapping("/bank")
@Api(tags = "银行卡管理")
public class BankController {

    @Autowired
    private UserBankService userBankService;

    @ApiOperation(value = "添加/修改银行卡", response = BankResp.class)
    @PostMapping
    @Authentication
    @CacheLock(prefix = "bank_save")
    public Result save(@ApiIgnore @CurrentUser User user, BankReq req) {
        UserBank bank = userBankService.save(user, req);
        return Result.success(BankResp.create(bank));
    }

    @ApiOperation(value = "查询银行卡", response = BankResp.class)
    @GetMapping("/list")
    @Authentication
    public Result list(@ApiIgnore @CurrentUser User user) {
        List<UserBank> list = Optional.ofNullable(userBankService.list(user)).orElse(Collections.emptyList());
        List<BankResp> respList = new ArrayList<>(list.size());
        list.forEach(bank -> {
            BankResp resp = BankResp.create(bank);
            if (resp != null) {
                respList.add(resp);
            }
        });
        return Result.success(respList);
    }


    @ApiOperation(value = "查询最新添加的一个银行卡", response = AccountResp.class)
    @GetMapping("/fresh")
    @Authentication
    public Result fresh(@ApiIgnore @CurrentUser User user) {
        UserBank bank = userBankService.getLatestAddOne(user.getId());
        BankResp resp = BankResp.create(bank);
        return Result.success(resp);
    }

}
