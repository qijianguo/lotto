package com.yincheng.game.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.enums.Role;
import com.yincheng.game.model.po.Task;
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
@RequestMapping("/lottery")
@Api(tags = "大乐透")
public class LotteryController {

    @Autowired
    private TaskService taskService;

    @ApiOperation("往期列表")
    @GetMapping("/period/list")
    public Result period(PeriodReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        req.setDesc("id");
        long size = req.getSize();
        if (size > 100) {
            req.setSize(100);
        }
        req.setCurrent(1);
        IPage<Task> periodPage = taskService.getPeriodPage(req);
        List<Task> records = periodPage.getRecords();
        return Result.success(convertFromPo(records));
    }

    @ApiOperation("往期列表(ADMIN)")
    @GetMapping("/period/all")
    @Authentication(roles = Role.ADMIN)
    public Result periods(PeriodReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        IPage<Task> periodPage = taskService.getPeriodPage(req);
        return Result.success(periodPage);
    }

    private List<PeriodResp> convertFromPo(List<Task> poList) {
        List<PeriodResp> respList = new ArrayList<>();
        poList.forEach(po -> {
            PeriodResp resp = new PeriodResp();
            BeanUtils.copyProperties(po, resp);
            respList.add(resp);
        });
        return respList;
    }

}
