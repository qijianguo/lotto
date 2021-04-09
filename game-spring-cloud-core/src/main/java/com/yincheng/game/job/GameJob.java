package com.yincheng.game.job;

import com.yincheng.game.Game;
import com.yincheng.game.context.GameContext;
import com.yincheng.game.context.GameContextHolder;
import com.yincheng.game.model.po.GameFlow;
import com.yincheng.game.model.po.Task;
import com.yincheng.game.model.vo.PeriodReq;
import com.yincheng.game.service.GameFlowService;
import com.yincheng.game.service.TaskService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏定时任务
 * @author qijianguo
 */
@Component
public class GameJob extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(GameJob.class);

    @Value("${game.job.enable:true}")
    private boolean gameJobEnabled;

    @Autowired
    private Game game;
    @Autowired
    private TaskService taskService;
    @Autowired
    private GameFlowService gameFlowService;

    private static Map<Integer, GameContext> contextMap = new HashMap<>();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        GameFlow gameFlow = (GameFlow) dataMap.get(GameJobManager.JOB_PARAM_NAME);
        gameFlow.initPeriod();
        run(gameFlow, context.getNextFireTime());
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
            game.run(gameFlow, context);
            logger.info("执行任务{}完毕，下次执行时间：{}", gameFlow.getName(), nextExecuteTime == null ? null : DateFormatUtils.format(nextExecuteTime, "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            logger.error(gameFlow.getName() + e.getMessage(), e);
            Task maxPeriod = taskService.getMaxPeriod(PeriodReq.create(gameFlow.getId(), 0));
            if (maxPeriod != null) {
                next = maxPeriod;
            }
        } finally {
            gameFlow.setNextPeriod(next.getPeriod());
            gameFlow.setTempPeriod(next.getPeriod() + 1);
            gameFlow.setUpdateTime(new Date());
            gameFlowService.updateById(gameFlow);
            contextMap.remove(next.getId());
            GameContextHolder.remove();
            logger.info("结束执行任务{}", gameFlow);
        }
    }

}
