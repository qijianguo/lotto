package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.Task;

public interface TaskService extends IService<Task> {

    Task getByGamePeriod(int gameId, long period);

    void updateResult(Task task);
}
