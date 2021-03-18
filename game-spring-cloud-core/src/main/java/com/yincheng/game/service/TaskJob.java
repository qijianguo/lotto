package com.yincheng.game.service;

import com.yincheng.game.concurent.GameFlowThreadPoolExecutor;
import com.yincheng.game.model.po.GameFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author qijianguo
 */
@Component
public class TaskJob {

    @Autowired
    private TaskService taskService;

    @PostConstruct
    private void init() {
        // 初始化游戏列表
        taskService.get

    }
}
