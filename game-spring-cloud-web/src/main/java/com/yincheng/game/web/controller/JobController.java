package com.yincheng.game.web.controller;

import com.yincheng.game.job.Constants;
import com.yincheng.game.model.Result;
import com.yincheng.game.model.vo.JobReq;
import com.yincheng.game.service.QuartzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "恢复游戏")
    @PutMapping("/resume")
    public Result resume(JobReq req) {
        quartzService.resumeJob(req.getJobName(), req.getGroupName());
        return Result.success();
    }

    @ApiOperation(value = "删除游戏")
    @PutMapping("/stop")
    public Result stop(JobReq req) {
        quartzService.deleteJob(req.getJobName(), req.getGroupName());
        return Result.success();
    }


}
