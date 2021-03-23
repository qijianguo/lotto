package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.Task;
import com.yincheng.game.model.vo.PeriodReq;

import java.util.List;

public interface TaskService extends IService<Task> {

    Task getByGamePeriod(int gameId, long period);

    void updateResult(Task task);

    IPage<Task> getPeriodPage(PeriodReq req);

    List<Task> getPeriod(PeriodReq req);

}
