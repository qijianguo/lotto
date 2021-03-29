package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.dao.mapper.TaskMapper;
import com.yincheng.game.model.enums.GameType;
import com.yincheng.game.model.po.Task;
import com.yincheng.game.model.vo.PeriodReq;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务
 * @author qijianguo
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    public Task getByGamePeriod(int gameId, long period) {
        return lambdaQuery()
                .eq(Task::getGameId, gameId)
                .eq(Task::getPeriod, period)
                .one();
    }

    @Override
    public void updateResult(String gameType, Task task) {
        List<Integer> nums = getNums(gameType);
        task.setPeriodResult(StringUtils.join(nums, ","), nums.stream().mapToInt(Integer::intValue).sum());
        lambdaUpdate().eq(Task::getId, task.getId())
                .update(task);
    }

    @Override
    public IPage<Task> getPeriodPage(PeriodReq req) {
        return lambdaQuery()
                .eq(req.getGameId() != null, Task::getGameId, req.getGameId())
                .eq(req.getStatus() != null, Task::getStatus, req.getStatus())
                .lt(req.getBeforeDate() != null, Task::getCreateTime, req.getBeforeDate())
                .gt(req.getAfterDate() != null, Task::getCreateTime, req.getAfterDate())
                .page(req);
    }

    @Override
    public List<Task> getPeriod(PeriodReq req) {
        IPage<Task> periodPage = getPeriodPage(req);
        return periodPage.getRecords();
    }

    @Override
    public Task getMaxPeriod(PeriodReq req) {
        req.setSize(1);
        req.setSearchCount(false);
        IPage<Task> page = lambdaQuery().eq(Task::getGameId, req.getGameId())
                .eq(Task::getStatus, req.getStatus())
                .orderByDesc(Task::getPeriod)
                .page(req);
        List<Task> records = page.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            return records.get(0);
        }
        return null;
    }

    private List<Integer> getNums(String gameType) {
        GameType match = GameType.match(gameType);
        if (match == null) {
            throw new IllegalArgumentException("game type not found, errGameType = " + gameType);
        }
        int length = -1;
        // 给出结果
        switch (match) {
            case _3D:
                length = 3;
                break;
            case _4D:
                length = 4;
                break;
            case _5D:
                length = 5;
                break;
            default:
        }
        List<Integer> nums = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            nums.add(RandomUtils.nextInt(0, 10));
        }
        return nums;
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            int i1 = RandomUtils.nextInt(0, 10);
            String key = String.valueOf(i1);
            if (map.containsKey(key)) {
                int val = map.get(key).intValue();
                map.put(key, val + 1);
            } else {
                map.put(key, 1);
            }
            System.out.println(i1);
        }
        System.out.println(map);
    }

}
