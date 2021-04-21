package com.yincheng.game.web.controller;

import com.yincheng.game.job.Constants;
import com.yincheng.game.model.Result;
import com.yincheng.game.model.po.GameConfig;
import com.yincheng.game.model.po.GameFlow;
import com.yincheng.game.model.vo.*;
import com.yincheng.game.service.GameConfigService;
import com.yincheng.game.service.GameFlowService;
import com.yincheng.game.service.QuartzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qijianguo
 */
@RestController
@RequestMapping("/game")
@Api(tags = "游戏管理")
public class GameController {

    @Autowired
    private GameFlowService gameFlowService;
    @Autowired
    private GameConfigService gameConfigService;
    @Autowired
    private QuartzService quartzService;

    @ApiOperation(value = "游戏列表", response = GameResp.class)
    @GetMapping("/list")
    public Result list() {
        List<GameFlow> list = gameFlowService.list();
        List<GameResp> respList = new ArrayList<>();
        list.forEach(game -> {
            GameResp resp = new GameResp(game);
            respList.add(resp);
        });
        return Result.success(respList);
    }

    @ApiOperation(value = "游戏配置", response = GameResp.class)
    @GetMapping("/config")
    public Result config() {
        List<GameConfig> list = gameConfigService.list();
        List<GameConfigResp> resps = new ArrayList<>();
        list.forEach(config -> {
            resps.add(new GameConfigResp(config));
        });
        return Result.success(resps);
    }

    @ApiOperation(value = "游戏保存/修改")
    @PostMapping
    public Result saveOrUpdate(GameReq req) {
        return Result.success();
    }

}
