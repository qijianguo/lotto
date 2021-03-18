package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.dao.mapper.TaskMapper;
import com.yincheng.game.model.po.Task;
import org.springframework.stereotype.Service;

/**
 * 任务
 * @author qijianguo
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    public Task getByGamePeriod(int gameId, long period) {
        return getOne(new QueryWrapper<Task>().eq("game_id", gameId).eq("period", period));
    }

    @Override
    public void updateResult(Task task) {
        updateById(task);
    }


}