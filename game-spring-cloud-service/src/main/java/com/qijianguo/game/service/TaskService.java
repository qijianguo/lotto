package com.qijianguo.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qijianguo.game.model.po.Task;
import com.qijianguo.game.model.vo.PeriodReq;

import java.util.List;

public interface TaskService extends IService<Task> {

    void saveTask(Task task);

    Task getByGamePeriod(int gameId, long period);

    void updateResult(String gameType, Task task);

    IPage<Task> getPeriodPage(PeriodReq req);

    List<Task> getPeriod(PeriodReq req);

    Task getMaxPeriod(PeriodReq req);

}
