package com.qijianguo.game.job;

import com.qijianguo.game.model.po.GameFlow;
import com.qijianguo.game.model.po.Task;
import com.qijianguo.game.service.BetHistoryService;
import com.qijianguo.game.service.QuartzService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qijianguo
 * 每期任务
 */
@Component
public class TaskJob extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(TaskJob.class);

    @Autowired
    private BetHistoryService betHistoryService;
    @Autowired
    private QuartzService quartzService;

    public void addTask(GameFlow gameFlow, Task task) {
        Map<String, Object> map = new HashMap<>(2);
        map.put(Constants.Task.MAP_KEY_TASK, task);
        map.put(Constants.Task.MAP_KEY_GAME_NAME, gameFlow.getName());
        quartzService.addJob(TaskJob.class, Constants.Task.jobName(task.getGameId(), task.getPeriod()), Constants.Task.groupName(), map);
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        Object o = dataMap.get(Constants.Task.MAP_KEY_TASK);
        Object gameName = dataMap.get(Constants.Task.MAP_KEY_GAME_NAME);
        if (o instanceof Task && gameName instanceof String) {
            Task task = (Task) o;
            String jobName = Constants.Task.jobName(task.getGameId(), task.getPeriod());
            String groupName = Constants.Task.groupName();
            logger.info("开始执行任务：{} {}", groupName, jobName);
            betHistoryService.settle((String) gameName, task, true);
            logger.info("结束执行任务：{} {}", groupName, jobName);
        }
    }
}
