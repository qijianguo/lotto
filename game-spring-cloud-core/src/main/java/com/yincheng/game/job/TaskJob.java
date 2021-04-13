package com.yincheng.game.job;

import com.yincheng.game.model.po.GameFlow;
import com.yincheng.game.model.po.Task;
import com.yincheng.game.service.BetHistoryService;
import com.yincheng.game.service.QuartzService;
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

import static com.yincheng.game.job.Constants.Task.*;

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
        map.put(MAP_KEY_TASK, task);
        map.put(MAP_KEY_GAME_NAME, gameFlow.getName());
        quartzService.addJob(TaskJob.class, jobName(task.getGameId(), task.getPeriod()), groupName(), 1, 1, map);
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        Object task = dataMap.get(MAP_KEY_TASK);
        Object gameName = dataMap.get(MAP_KEY_GAME_NAME);
        if (task instanceof Task && gameName instanceof String) {
            betHistoryService.settle((String) gameName, (Task) task, true);
        }
    }
}
