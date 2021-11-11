package com.qijianguo.game.job;

import com.qijianguo.game.context.GameContext;
import com.qijianguo.game.context.GameContextHolder;
import com.qijianguo.game.model.enums.Destination;
import com.qijianguo.game.model.po.GameFlow;
import com.qijianguo.game.model.po.Task;
import com.qijianguo.game.model.vo.PeriodReq;
import com.qijianguo.game.model.vo.RecentTaskResp;
import com.qijianguo.game.service.GameFlowService;
import com.qijianguo.game.service.TaskService;
import com.qijianguo.game.service.WebSocketService;
import com.yincheng.game.service.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author qijianguo
 */
@Service
public class GameFlowJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(GameFlowJob.class);

    @Autowired
    private GameFlowService gameFlowService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private TaskJob taskJob;

    private static Map<Integer, GameContext> contextMap = new HashMap<>();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        Object o = dataMap.get(Constants.Game.MAP_KEY_GAME);
        if (o instanceof GameFlow) {
            GameFlow gameFlow = (GameFlow)o;
            gameFlow = gameFlowService.getById(gameFlow.getId());
            gameFlow.initPeriod();
            run(gameFlow, context.getNextFireTime());
        }
    }

    /**
     * 执行任务
     */
    public void run(GameFlow gameFlow, Date nextExecuteTime) {
        logger.info("开始执行任务{}", gameFlow);
        GameJobContext context;
        Task next = Task.initNext(gameFlow, nextExecuteTime);
        try {
            taskService.saveTask(next);
            context = GameJobContext.create(gameFlow.getId(), next);
            GameContextHolder.set(context);
            contextMap.put(next.getId(), context);
            run(gameFlow, context);
            logger.info("执行任务{}完毕，下次执行时间：{}", gameFlow.getName(), nextExecuteTime == null ? null : DateFormatUtils.format(nextExecuteTime, "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            logger.error(gameFlow.getName() + e.getMessage(), e);
            Task maxPeriod = taskService.getMaxPeriod(PeriodReq.create(gameFlow.getId(), 0));
            if (maxPeriod != null) {
                next = maxPeriod;
            }
        } finally {
            gameFlow.setCurrPeriod(next.getPeriod());
            gameFlow.setNextPeriod(next.getPeriod() + 1);
            gameFlow.setUpdateTime(new Date());
            gameFlowService.updateById(gameFlow);
            contextMap.remove(next.getId());
            GameContextHolder.remove();
            logger.info("结束执行任务{}", gameFlow.getName());
        }
    }

    public void run(GameFlow gameFlow, GameContext context) {
        // 触发监听器
        Long prevPeriod = Optional.ofNullable(gameFlow.getCurrPeriod()).orElse(-1L);
        Task prevTask = null;
        // 计算上一期的结果
        if (prevPeriod != -1) {
            prevTask = taskService.getByGamePeriod(gameFlow.getId(), prevPeriod);
            if (prevTask != null && prevTask.getStatus() == 0) {
                taskService.updateResult(gameFlow.getType(), prevTask);
                gameFlow.setResult(prevTask.getResult());
                gameFlow.setSum(prevTask.getSum());
                gameFlow.setPrevPeriod(gameFlow.getCurrPeriod());
            }
        }
        // 通过ws发送给客户端
        RecentTaskResp resp = new RecentTaskResp(prevTask, context.getNextTask());
        webSocketService.send(Destination.resultTopic(gameFlow.getType()), resp);
        taskJob.addTask(gameFlow, prevTask);
    }

}
