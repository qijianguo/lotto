package com.yincheng.game.service;

import com.yincheng.game.model.po.Task;
import com.yincheng.game.model.vo.PeriodReq;
import org.quartz.DateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author qijianguo
 */
@Component
public class TaskJob {

    private static Logger logger = LoggerFactory.getLogger(TaskJob.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private BetHistoryService betHistoryService;

    @Scheduled(cron = "0/20 * * * * ?")
    public void syncBook() {
        PeriodReq req = new PeriodReq();
        req.setStatus(0);
        req.setBeforeDate(DateBuilder.futureDate(-2, DateBuilder.IntervalUnit.MINUTE));
        req.setSearchCount(true);
        List<Task> tasks = taskService.getPeriod(req);
        if (!CollectionUtils.isEmpty(tasks)) {
            logger.info("TaskJob start: size={}", tasks.size());
            tasks.forEach(task -> betHistoryService.settle(task));
            logger.info("TaskJob end", tasks.size());
        }
    }
}
