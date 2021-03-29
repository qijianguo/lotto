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

    public void pauseJob(Integer gameId) {
        String jobKey = getJobKey(gameId);
        try {
            scheduler.pauseJob(new JobKey(jobKey));
        } catch (SchedulerException e) {
            logger.error("停止定时任务出错", e);
        }
    }

    public void resume(Integer gameId) {
        String jobKey = getJobKey(gameId);
        try {
            scheduler.resumeJob(new JobKey(jobKey));
        } catch (SchedulerException e) {
            logger.error("恢复定时任务出错", e);
        }
    }

    public void delete(Integer gameId) {
        String jobKey = getJobKey(gameId);
        try {
            scheduler.deleteJob(new JobKey(jobKey));
        } catch (SchedulerException e) {
            logger.error("删除定时任务出错", e);
        }
    }

    private String getJobKey(long id) {
        return JOB_NAME + id;
    }


}
