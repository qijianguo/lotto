package com.yincheng.game.job;

import com.yincheng.game.model.po.GameFlow;
import com.yincheng.game.service.GameFlowService;
import com.yincheng.game.service.QuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yincheng.game.job.Constants.Game.*;

/**
 * @author qijianguo
 * 游戏任务初始化
 */
@Component
public class GameFlowJobManager {

    private static final Logger logger = LoggerFactory.getLogger(GameFlowJobManager.class);

    @Autowired
    private GameFlowService gameFlowService;
    @Autowired
    private QuartzService quartzService;

    @PostConstruct
    private void init() {
        List<GameFlow> gameFlowList = gameFlowService.getAllEnabled();
        if (CollectionUtils.isEmpty(gameFlowList)) {
            logger.warn("game list is empty!");
            return;
        }
        gameFlowList.forEach(game -> addJob(game));
    }

    public void addJob(GameFlow game) {
        Map<String, GameFlow> map = new HashMap<>(1);
        map.put(MAP_KEY_GAME, game);
        String jobName = jobName(game.getId());
        quartzService.addJob(GameFlowJob.class, jobName, jobName, game.getCron(), map);
    }

}
