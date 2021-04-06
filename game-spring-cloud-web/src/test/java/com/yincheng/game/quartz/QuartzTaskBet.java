package com.yincheng.game.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuartzTaskBet {

    private static Logger logger = LoggerFactory.getLogger(QuartzTaskBet.class);

    @Test
    public void helloWord() throws SchedulerException, InterruptedException {
        System.out.println("start:"+ LocalDateTime.now());
        // 创建一个Schedule
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        setJobAndTrigger(scheduler);

        Thread.sleep(20000);
        scheduler.shutdown();
        System.out.println("stop:"+ LocalDateTime.now());
    }

    public void listener() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        setJobAndTrigger(scheduler);
        HelloJobListenerImpl helloJobListener = new HelloJobListenerImpl();
        scheduler.getListenerManager().addJobListener(helloJobListener, KeyMatcher.keyEquals(new JobKey("myJobName", "myJobGroup")));

    }

    private void setJobAndTrigger(Scheduler scheduler) throws SchedulerException {
        scheduler.getContext().put("k1", "kv1");

        Date startTime = DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND);
        Date endTime = DateBuilder.futureDate(1, DateBuilder.IntervalUnit.DAY);
        // 创建一个Trigger
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .usingJobData("t1", "tv1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withMisfireHandlingInstructionFireNow()
                        .withRepeatCount(10))
                .endAt(endTime)
                .build();
        simpleTrigger.getJobDataMap().put("t2", "tv2");

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(10, 11)
                        .withMisfireHandlingInstructionFireAndProceed())
                .startAt(startTime)
                .endAt(endTime)
                .build();


        // 创建一个Job
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .usingJobData("j1", "jv1")
                .withIdentity("myjob", "mygroup")
                // job是否是持久的，默认为false，如果为false，当没有与之关联的trigger时会自动从schedule中删除
                .storeDurably()
                // job是否是可恢复的，默认为false，如果是true，则宕机、关机发生硬关闭重新启动后job会被重新执行
                .requestRecovery()
                .build();

        job.getJobDataMap().put("j2", "jv2");

        // 注册trigger
        scheduler.scheduleJob(job, simpleTrigger);
        //启动schedule
        scheduler.start();

    }

    private void post(Scheduler scheduler) {

    }

    private void after(Scheduler scheduler) {

    }

}


