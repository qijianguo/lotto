package com.yincheng.game;

import com.alibaba.ttl.TtlRunnable;
import com.yincheng.game.concurent.GameFlowThreadPoolExecutor;
import com.yincheng.game.concurent.GameFlowThreadPoolExecutor.SubThreadPoolExecutor;
import com.yincheng.game.concurent.LinkedThreadSubmitStrategy;
import com.yincheng.game.concurent.ThreadSubmitStrategy;
import com.yincheng.game.context.GameContext;
import com.yincheng.game.job.GameJobManager;
import com.yincheng.game.model.enums.Destination;
import com.yincheng.game.model.enums.GameType;
import com.yincheng.game.model.po.BetHistory;
import com.yincheng.game.service.*;
import com.yincheng.game.job.GameJobContext;
import com.yincheng.game.listener.GameListener;
import com.yincheng.game.model.po.GameFlow;
import com.yincheng.game.model.po.Task;
import com.yincheng.game.model.vo.TaskWsResp;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 游戏的核心类
 *
 * @author qijianguo
 */
@Component
public class Game {

    private static Logger logger = LoggerFactory.getLogger(Game.class);

    private static GameFlowThreadPoolExecutor executorInstance;
    @Value("${game.thread.max:64}")
    private Integer totalThreads;
    @Value("${game.thread.default:8}")
    private Integer defaultThreads;
    @Value("${spider.detect.dead-cycle:5000}")
    private Integer deadCycle;

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
        executorInstance = new GameFlowThreadPoolExecutor(totalThreads);

        // 初始化游戏列表
        List<GameFlow> gameFlowList = gameFlowService.getAllEnabled();
        if (CollectionUtils.isEmpty(gameFlowList)) {
            logger.warn("game list is empty!");
            return;
        }
        gameFlowList.forEach(game -> {
            gameJobManager.addJob(game);
        });

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
            if (prevTask != null || prevTask.getStatus() == 0) {
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

    public void run2(GameFlow gameFlow, GameContext context, Map<String, Object> variables) {
        // 当前流程执行线程数
        int threads = 4;
        // 线程提交策略
        ThreadSubmitStrategy submitStrategy = new LinkedThreadSubmitStrategy();
        // 创建子线程, 采用一父多子的线程池，子线程数不能超过总线程数（超过则进入队列等待）
        SubThreadPoolExecutor pool = executorInstance.createSubThreadPool(Math.max(threads, 1) + 1, submitStrategy);
        context.setThreadPool(pool);
        // 出发监听器
        if (!CollectionUtils.isEmpty(listeners)) {
            listeners.forEach(listener -> listener.beforeStart(context));
        }
        Future<Object> future = pool.submitAsync(TtlRunnable.get(() -> {
          /*  // TODO 上一期结束
            logger.info("游戏【{}-{}】结束！", gameFlow.getName(), gameFlow.getNextPeriod());
            // TODO 下一期开始
            logger.info("游戏【{}-{}】开始！", gameFlow.getName(), gameFlow.getNextPeriod());*/
        }), null, null);
        try {
            // 等待所有任务完成
            future.get();
        } catch (InterruptedException | ExecutionException ignore) {}
    }

    private void setResult(String gameType, Task task) {
        int length = -1;
        // 给出结果
        switch (GameType.match(gameType)) {
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
                break;
        }
        if (length <= 0) {
            throw new IllegalArgumentException("game type not found, errGameType = " + gameType);
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

    public static void main(String[] args) {
        String v = "4,9,2";
        List<Integer> collect = Arrays.stream(v.split(",")).map(Integer::new).collect(Collectors.toList());

        System.out.println(collect);
    }
}
