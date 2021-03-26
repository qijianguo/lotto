package com.yincheng.game.service;

import com.yincheng.game.job.GameJob;
import com.yincheng.game.model.po.GameFlow;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskJobManager {


    private static Logger logger = LoggerFactory.getLogger(TaskJobManager.class);

    /**
     * 调度器
     */
    @Autowired
    private Scheduler scheduler;

    private static final String JOB_NAME = "TASK_";

    public static final String JOB_PARAM_NAME = "GAME_FLOW";

    /**
     */
    public Date addJob() {
        try {
            JobDetail job = JobBuilder.newJob(GameJob.class)
                    .withIdentity(JOB_NAME)
                    .build();
            SimpleScheduleBuilder schedule = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(30)
                    .repeatForever();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(JOB_NAME).withSchedule(schedule).startAt(new Date()).build();
            return scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            logger.error("创建定时任务出错", e);
            return null;
        }
    }

}
