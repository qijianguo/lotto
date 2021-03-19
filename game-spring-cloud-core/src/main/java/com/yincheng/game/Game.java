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
        Long prevPeriod = Optional.ofNullable(gameFlow.getPeriod()).orElse(-1L);
        Task prevTask = null;
        // 计算上一期的结果
        if (prevPeriod != -1) {
            prevTask = taskService.getByGamePeriod(gameFlow.getId(), gameFlow.getNextPeriod());
            if (prevTask != null && prevTask.getStatus() == 0) {
                setResult(gameFlow.getType(), prevTask);
                taskService.updateResult(prevTask);
                gameFlow.setResult(prevTask.getResult());
                gameFlow.setPeriod(gameFlow.getNextPeriod());
            }
        }
        // 通过ws发送给客户端
        TaskWsResp resp = new TaskWsResp(prevTask, context.getNextTask());
        webSocketService.send(Destination.gameResult(gameFlow.getType()), resp);
        if (prevTask != null) {
            // TODO 发放上一期奖励
            betHistoryService.settle(prevTask, true);
        }
    }

    private void setResult(String gameType, Task task) {
        GameType match = GameType.match(gameType);
        if (match == null) {
            throw new IllegalArgumentException("game type not found, errGameType = " + gameType);
        }
        int length = -1;
        // 给出结果
        switch (match) {
            case _3D:
                length = 3;
                break;
            case _4D:
                length = 4;
                break;
            case _5D:
                length = 5;
                break;
            default:
        }
        List<Integer> nums = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            nums.add(getSingleNumber());
        }
        task.setResult(StringUtils.join(nums, ","));
        task.setSum(nums.stream().mapToInt(Integer::intValue).sum());
        task.setStatus(1);
        task.setUpdateTime(new Date());
    }

    private Integer getSingleNumber() {
        // 随机生成4位数
        return RandomUtils.nextInt(0, 1000) % 10;
    }

}
