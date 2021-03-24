package com.yincheng.game;

import com.yincheng.game.job.GameJobManager;
import com.yincheng.game.model.enums.Destination;
import com.yincheng.game.model.enums.GameType;
import com.yincheng.game.service.*;
import com.yincheng.game.job.GameJobContext;
import com.yincheng.game.listener.GameListener;
import com.yincheng.game.model.po.GameFlow;
import com.yincheng.game.model.po.Task;
import com.yincheng.game.model.vo.TaskWsResp;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 游戏的核心类
 *
 * @author qijianguo
 */
@Component
public class Game {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    @Autowired(required = false)
    private List<GameListener> listeners;
    @Autowired
    private FlowNoticeService flowNoticeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private BetHistoryService betHistoryService;
    @Autowired
    private GameFlowService gameFlowService;
    @Autowired
    private GameJobManager gameJobManager;

    @PostConstruct
    private void init() {
        // 初始化游戏列表
        List<GameFlow> gameFlowList = gameFlowService.getAllEnabled();
        if (CollectionUtils.isEmpty(gameFlowList)) {
            logger.warn("game list is empty!");
            return;
        }
        gameFlowList.forEach(game -> gameJobManager.addJob(game));
    }

    public void run(GameFlow gameFlow, GameJobContext context) {
        flowNoticeService.sendFlowNotice();
        run(gameFlow, context, new HashMap<>(6));
        flowNoticeService.sendFlowNotice();
    }

    private void run(GameFlow gameFlow, GameJobContext context, Map<String, Object> variables) {
        // 触发监听器
        if (!CollectionUtils.isEmpty(listeners)) {
            listeners.forEach(listener -> listener.beforeStart(context));
        }
        Long prevPeriod = Optional.ofNullable(gameFlow.getNextPeriod()).orElse(-1L);
        Task period = null;
        // 计算上一期的结果
        if (prevPeriod != -1) {
            period = taskService.getByGamePeriod(gameFlow.getId(), prevPeriod);
            if (period != null && period.getStatus() == 0) {
                taskService.updateResult(gameFlow.getType(), period);
                gameFlow.setResult(period.getResult());
                gameFlow.setPeriod(gameFlow.getNextPeriod());
            }
        }
        // 通过ws发送给客户端
        TaskWsResp resp = new TaskWsResp(period, context.getNextTask());
        webSocketService.send(Destination.gameResult(gameFlow.getType()), resp);
        if (period != null) {
            // TODO 发放上一期奖励
            betHistoryService.settle(period, true);
        }
    }

}
