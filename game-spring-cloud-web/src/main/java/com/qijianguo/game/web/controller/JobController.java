package com.qijianguo.game.web.controller;

import com.qijianguo.game.common.exception.BusinessException;
import com.qijianguo.game.common.exception.EmBusinessError;
import com.qijianguo.game.job.Constants;
import com.qijianguo.game.job.GameFlowJobManager;
import com.qijianguo.game.model.Result;
import com.qijianguo.game.model.po.GameFlow;
import com.qijianguo.game.model.vo.JobReq;
import com.qijianguo.game.service.GameFlowService;
import com.qijianguo.game.service.QuartzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author qijianguo
 */
@RestController
@RequestMapping("/job")
@Api(tags = "任务管理")
public class JobController {

    @Autowired
    private QuartzService quartzService;
    @Autowired
    private GameFlowService gameFlowService;
    @Autowired
    private GameFlowJobManager gameFlowJobManager;

    @ApiOperation(value = "所有job")
    @GetMapping("/list")
    public Result list() {
        List<Map<String, Object>> maps = quartzService.queryAllJob();
        return Result.success(maps);
    }

    @ApiOperation(value = "所有运行中的job")
    @GetMapping("/list/running")
    public Result listEnabled() {
        List<Map<String, Object>> maps = quartzService.queryRunJob();
        return Result.success(maps);
    }

    @ApiOperation(value = "暂停Job")
    @PutMapping("/pause")
    public Result pause(JobReq req) {
        quartzService.pauseJob(req.getJobName(), req.getGroupName());
        return Result.success();
    }

    @ApiOperation(value = "恢复Job")
    @PutMapping("/resume")
    public Result resume(JobReq req) {
        quartzService.resumeJob(req.getJobName(), req.getGroupName());
        return Result.success();
    }

    @ApiOperation(value = "删除Job")
    @PutMapping("/stop")
    public Result stop(JobReq req) {
        quartzService.deleteJob(req.getJobName(), req.getGroupName());
        return Result.success();
    }

    @ApiOperation(value = "同步游戏任务")
    @PostMapping("/game/sync")
    public Result sync(Integer gameId) {
        GameFlow game = gameFlowService.getById(gameId);
        if (game == null) {
            throw new BusinessException(EmBusinessError.SOURCE_NOT_FOUND);
        }
        String jobName = Constants.Game.jobName(gameId);
        if (game.getEnabled() == 0) {
            quartzService.deleteJob(jobName, jobName);
        } else {
            boolean exists = quartzService.exists(jobName, jobName);
            if (exists) {
                quartzService.updateJobCron(jobName, jobName, game.getCron());
            } else {
                gameFlowJobManager.addJob(game);
            }
        }
        return Result.success("game sync success.");
    }

}
