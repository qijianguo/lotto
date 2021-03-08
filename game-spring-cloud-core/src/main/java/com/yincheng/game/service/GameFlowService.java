package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.job.GameJobManager;
import com.yincheng.game.mapper.GameFlowMapper;
import com.yincheng.game.model.po.GameFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 游戏流程执行服务
 * @author qijianguo
 */
@Service
public class GameFlowService extends ServiceImpl<GameFlowMapper, GameFlow> {

    private static Logger logger = LoggerFactory.getLogger(GameFlowService.class);

    @Autowired
    private GameFlowMapper gameFlowMapper;
    @Autowired
    private GameJobManager gameJobManager;
    @Autowired
    private TaskService taskService;

    /**
     * 项目启动后自动查询DB配置，并创建游戏任务
     */
    @PostConstruct
    public void initJobs() {
        List<GameFlow> gameFlowList = gameFlowMapper.selectList(new QueryWrapper<GameFlow>().eq("enabled", 1));
        if (CollectionUtils.isEmpty(gameFlowList)) {
            logger.warn("game list is empty!");
            return;
        }
        gameFlowList.forEach(game -> {
            gameJobManager.addJob(game);
        });
    }

}
