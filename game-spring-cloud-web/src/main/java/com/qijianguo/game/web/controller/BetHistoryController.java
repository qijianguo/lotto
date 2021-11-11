package com.qijianguo.game.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qijianguo.game.model.Result;
import com.qijianguo.game.model.anno.Authentication;
import com.qijianguo.game.model.anno.CacheLock;
import com.qijianguo.game.model.anno.CurrentUser;
import com.qijianguo.game.model.po.Account;
import com.qijianguo.game.model.po.BetHistory;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.vo.BetAddReq;
import com.qijianguo.game.model.vo.BetHistoryResp;
import com.qijianguo.game.model.vo.BetReq;
import com.qijianguo.game.model.vo.BetResp;
import com.qijianguo.game.service.BetHistoryService;
import com.yincheng.game.model.vo.*;
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
@RequestMapping("/bet")
@Api(tags = "下注管理")
public class BetHistoryController {

    @Autowired
    private BetHistoryService betHistoryService;

    @ApiOperation(value = "下注", response = BetResp.class)
    @PostMapping
    @Authentication
    @CacheLock(prefix = "bet_add")
    public Result add(@ApiIgnore @CurrentUser User user, BetAddReq req) {
        Account bet = betHistoryService.bet(user, req);
        BetResp resp = new BetResp();
        resp.setBalance(bet.getBalance());
        return Result.success(resp);
    }

    @ApiOperation(value = "我的下注列表", response = BetHistoryResp.class)
    @GetMapping
    @Authentication
    public Result list(@ApiIgnore @CurrentUser User user, BetReq req) {
        IPage<BetHistory> page = betHistoryService.list(user, req);
        IPage<BetHistoryResp> result = new Page<>();
        BeanUtils.copyProperties(page, result);
        result.setRecords(convertFromPo(page.getRecords()));
        return Result.success(result);
    }


    private List<BetHistoryResp> convertFromPo(List<BetHistory> poList) {
        List<BetHistoryResp> respList = new ArrayList<>();
        poList.forEach(po -> {
            BetHistoryResp resp = new BetHistoryResp();
            BeanUtils.copyProperties(po, resp);
            respList.add(resp);
        });
        return respList;
    }


}
