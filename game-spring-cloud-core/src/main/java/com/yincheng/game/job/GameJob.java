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
        logger.info("开始执行任务{}", gameFlow.getName());
        GameJobContext context;
        Task task = new Task();
        task.setGameId(gameFlow.getId());
        task.setStartTime(new Date());
        task.setEndTime(nextExecuteTime);
        task.setPeriod(gameFlow.getTempPeriod());
        try {
            taskService.save(task);
            context = GameJobContext.create(gameFlow.getId(), task);
            GameContextHolder.set(context);
            contextMap.put(task.getId(), context);
            game.run(gameFlow, context);
            logger.info("执行任务{}完毕，下次执行时间：{}", gameFlow.getName(), nextExecuteTime == null ? null : DateFormatUtils.format(nextExecuteTime, "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            logger.error("执行任务{}出错", gameFlow.getName(), e);
            PeriodReq req = new PeriodReq();
            req.setGameId(gameFlow.getId());
            req.setStatus(0);
            Task maxPeriod = taskService.getMaxPeriod(req);
            if (maxPeriod != null) {
                task = maxPeriod;
            }
        } finally {
            gameFlow.setNextPeriod(task.getPeriod());
            gameFlow.setTempPeriod(task.getPeriod() + 1);
            gameFlowService.updateById(gameFlow);
            contextMap.remove(task.getId());
            GameContextHolder.remove();
        }
    }

}
