package com.yincheng.game.web.controller;

import com.yincheng.game.model.po.BetHistory;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.AccountResp;
import com.yincheng.game.model.vo.BetReq;
import com.yincheng.game.service.BetHistoryService;
import com.yincheng.game.model.vo.BetAddReq;
import com.yincheng.game.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qijianguo
 */
@RestController
@RequestMapping("/bet")
@Api(tags = "下注历史")
public class BetHistoryController {

    @Autowired
    private BetHistoryService betHistoryService;

    @ApiOperation(value = "添加")
    @PostMapping("/save")
    public Result add(BetAddReq req) {
        User user = new User();
        user.setId(1);
        AccountResp resp = new AccountResp(betHistoryService.bet(user, req));
        return Result.success(resp);
    }

    @ApiOperation(value = "我的下注")
    @GetMapping("/list")
    public Result list(BetReq req) {
        User user = new User();
        user.setId(1);
        List<BetHistory> list = betHistoryService.list(user, req);

        return Result.success();
    }


}
