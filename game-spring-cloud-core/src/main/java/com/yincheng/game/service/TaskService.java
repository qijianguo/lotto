package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.mapper.TaskMapper;
import com.yincheng.game.model.po.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 任务
 * @author qijianguo
 */
@Service
public class TaskService extends ServiceImpl<TaskMapper, Task> {

    @Autowired
    private TaskMapper taskMapper;

    public Task finish(int gameId, long period, String result) {
        Task task = taskMapper.selectOne(new QueryWrapper<Task>().eq("game_id", gameId).eq("period", period));
        if (task == null) {
            return null;
        }
        if (task.getStatus() == 1) {
            return task;
        }
        task.setResult(result);
        task.setStatus(1);
        task.setUpdateTime(new Date());
        taskMapper.updateById(task);
        return task;
    }


}
