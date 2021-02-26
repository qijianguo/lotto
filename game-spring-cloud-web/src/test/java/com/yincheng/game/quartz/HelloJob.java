package com.yincheng.game.quartz;

import org.quartz.*;

import java.time.LocalDateTime;

/**
 * 实现Job接口，表明该自定义job需要完成什么类型的任务
 *
 * 注意
 * 1.当使用默认的JobFactory时，Job必须有一个无参的构造函数
 * 2.不应该定义有状态的数据属性，因为每一次Job执行都会创建新的实例，执行完成后实例引用就会被丢弃，随后被垃圾回收。
 *
 * 如果要给job添加属性或配置，可以保存在JobDataMap中（JobDataMap是JobDetail的一部分）
 *
 * Job状态和并发控制：
 * @DisallowConcurrentExecution 不要并发的执行Job，即同一时刻只允许一个实例
 * @PersistJobDataAfterExecution 成功执行完execute方法后更新JobDataMap的数据，使得下一次执行使用的是更新后的数据，而不是旧数据
 * 尽管注解是加在job类上的，但其限制作用是针对job实例的，而不是job类的
 * 如果使用了@PersistJobDataAfterExecution注解，强烈建议同时使用@DisallowConcurrentExecution注解，
 * 因为当同一个job（JobDetail）的两个实例被并发执行时，由于竞争，JobDataMap中存储的数据很可能是不确定的。
 *
 * JobExecutionException execute方法只允许抛出JobExecutionException一种异常，因此应该将execute的所有内容放在try...catch块中
 *
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class HelloJob implements Job {

    public HelloJob(){}

    @Override
    public void execute(JobExecutionContext context) {
        try {
            System.out.println("hello:"+ LocalDateTime.now());
            Object tv1 = context.getTrigger().getJobDataMap().get("t1");
            Object tv2 = context.getTrigger().getJobDataMap().get("t2");
            Object jv1 = context.getJobDetail().getJobDataMap().get("j1");
            Object jv2 = context.getJobDetail().getJobDataMap().get("j2");
            Object kv1 = null;
            try {
                kv1 = context.getScheduler().getContext().get("k1");
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            System.out.println(tv1+":"+tv2);
            System.out.println(jv1+":"+jv2);
            System.out.println(kv1);
            System.out.println("hello:"+ LocalDateTime.now());

            // job和trigger的JobDataMap的合集，但是如果key一样，则trigger会覆盖job的数据
            JobDataMap all = context.getMergedJobDataMap();
            Object tv11 = all.get("t1");
            Object tv21 = all.get("t2");
            Object jv11 = all.get("j1");
            Object jv21 = all.get("j2");
            Object kv11 = all.get("k1");
            System.out.println(tv11+":"+tv21);
            System.out.println(jv11+":"+jv21);
            System.out.println(kv11);
            System.out.println("hello:"+ LocalDateTime.now());
        } catch (Exception e) {
            // 告诉schedule接下来应该处理异常
        }

    }


}
