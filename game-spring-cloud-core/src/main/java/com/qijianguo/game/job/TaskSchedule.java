package com.qijianguo.game.job;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qijianguo.game.model.po.BetHistory;
import com.qijianguo.game.model.po.Task;
import com.qijianguo.game.service.BetHistoryService;
import com.qijianguo.game.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qijianguo
 */
@Component
public class TaskSchedule {

    private static Logger logger = LoggerFactory.getLogger(TaskSchedule.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private BetHistoryService betHistoryService;

    /*@Scheduled(cron = "0/20 * * * * ?")
    public void noResultCal() {
        PeriodReq req = new PeriodReq();
        req.setStatus(0);
        req.setBeforeDate(DateBuilder.futureDate(-3, DateBuilder.IntervalUnit.MINUTE));
        req.setSearchCount(false);
        List<Task> tasks = taskService.getPeriod(req);
        if (!CollectionUtils.isEmpty(tasks)) {
            logger.info("TaskJob start: size={}", tasks.size());
            tasks.forEach(task -> betHistoryService.settle(task));
            logger.info("TaskJob end", tasks.size());
        }
    }*/

    @Scheduled(cron = "30 * * * * ?")
    public void noSuccessBet() {
        IPage<BetHistory> page = betHistoryService.lambdaQuery().eq(BetHistory::getStatus, 0).page(new Page<>(1, 500, false));
        Map<String, Task> map = new HashMap<>(60);
        page.getRecords().forEach(bet -> {
            String key = bet.getGameId() + "-" + bet.getPeriod();
            Task task;
            if (map.containsKey(key)) {
                task = map.get(key);
            } else {
                task = taskService.getByGamePeriod(bet.getGameId(), bet.getPeriod());
                map.put(key, task);
            }
            if (task.getStatus() == 1) {
                List<Integer> result = Arrays.stream(task.getResult().split(",")).map(Integer::new).collect(Collectors.toList());
                betHistoryService.settle(bet, result);
            }
        });

    }
}
