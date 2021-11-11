package com.qijianguo.game.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qijianguo.game.common.exception.BusinessException;
import com.qijianguo.game.common.exception.EmBusinessError;
import com.qijianguo.game.model.Result;
import com.qijianguo.game.model.anno.Authentication;
import com.qijianguo.game.model.enums.Role;
import com.qijianguo.game.model.po.Task;
import com.qijianguo.game.model.vo.BetResp;
import com.qijianguo.game.model.vo.PeriodReq;
import com.qijianguo.game.model.vo.RecentTaskResp;
import com.qijianguo.game.model.vo.TaskResp;
import com.qijianguo.game.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        req.setSize(1);
        req.setStatus(0);
        //List<Task> records = taskService.getPeriod(req);
        Task curr = taskService.getMaxPeriod(req);
        req.setStatus(1);
        Task prev = taskService.getMaxPeriod(req);
        RecentTaskResp resp = new RecentTaskResp(prev, curr);
        return Result.success(resp);
    }

    @ApiOperation("往期列表")
    @GetMapping("/list")
    public Result period(PeriodReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        req.setDesc("id");
        req.setStatus(1);
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

    private List<TaskResp> convert(List<Task> records) {
        List<TaskResp> respList = new ArrayList<>();
        records.forEach(po -> respList.add(new TaskResp(po)));
        return respList;
    }

}
