package com.yincheng.game.job;

import com.yincheng.game.model.po.GameFlow;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 游戏任务执行管理器
 * @author qijianguo
 */
@Component
public class GameJobManager {

    private static Logger logger = LoggerFactory.getLogger(GameJobManager.class);

    /**
     * 调度器
     */
    @Autowired
    private Scheduler scheduler;

    private static final String JOB_NAME = "GAME_TASK_";

    public static final String JOB_PARAM_NAME = "GAME_FLOW";

    /**
     * 添加游戏任务
     * @param gameFlow
     * @return
     */
    public Date addJob(GameFlow gameFlow) {
        try {
            JobDetail job = JobBuilder.newJob(GameJob.class)
                    .withIdentity(getJobKey(gameFlow.getId()))
                    .build();
            job.getJobDataMap().put(JOB_PARAM_NAME, gameFlow);

            CronScheduleBuilder schedule = CronScheduleBuilder.cronSchedule(gameFlow.getCron()).withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getJobKey(gameFlow.getId()))
                    .withSchedule(schedule)
                    .build();

            /*SimpleScheduleBuilder schedule = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(10)
                    .withRepeatCount(0);
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(getJobKey(gameFlow.getId())).withSchedule(schedule).startAt(new Date()).build();*/

            return scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            logger.error("创建定时任务出错", e);
            return null;
        }
    }

    /**
     * 重新开始任务
     * @param gameFlow
     * @return
     */
    public Date restartJob(GameFlow gameFlow) {
        try {
            String jobKey = getJobKey(gameFlow.getId());
            SimpleScheduleBuilder schedule = SimpleScheduleBuilder.simpleSchedule()
                    .repeatForever();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getJobKey(gameFlow.getId()))
                    .startNow()
                    .withSchedule(schedule).build();
            return scheduler.rescheduleJob(new TriggerKey(jobKey), trigger);
        } catch (Exception e) {
            logger.error("重新创建定时任务出错", e);
            return null;
        }
    }

    private String getJobKey(long id) {
        return JOB_NAME + id;
    }


}
