package com.yincheng.game.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.enums.Role;
import com.yincheng.game.model.po.Task;
import com.yincheng.game.model.vo.BetResp;
import com.yincheng.game.model.vo.PeriodReq;
import com.yincheng.game.model.vo.PeriodResp;
import com.yincheng.game.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qijianguo
 */
@RestController
@RequestMapping("/period")
@Api(tags = "往期管理")
public class PeriodController {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "上期&本期", response = BetResp.class)
    @GetMapping("/current")
    public Result current(Integer gameId) {
        PeriodReq req = new PeriodReq();
        req.setGameId(gameId);
        req.setDesc("period");
        req.setSize(2);
        List<Task> records = taskService.getPeriod(req);
        return Result.success(convert(records));
    }

    @ApiOperation("往期列表")
    @GetMapping("/list")
    public Result period(PeriodReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        req.setDesc("id");
        List<Task> records = taskService.getPeriod(req);
        return Result.success(convert(records));
    }

    @ApiOperation("往期列表(ADMIN)")
    @GetMapping("/all")
    @Authentication(roles = Role.ADMIN)
    public Result periods(PeriodReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        IPage<Task> periodPage = taskService.getPeriodPage(req);
        return Result.success(periodPage);
    }

    private List<PeriodResp> convert(List<Task> records) {
        List<PeriodResp> respList = new ArrayList<>();
        records.forEach(po -> {
            PeriodResp resp = new PeriodResp();
            BeanUtils.copyProperties(po, resp);
            respList.add(resp);
        });
        return respList;
    }

}
