package com.qijianguo.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qijianguo.game.common.exception.BusinessException;
import com.qijianguo.game.common.util.RandomUtils;
import com.qijianguo.game.dao.mapper.TaskMapper;
import com.qijianguo.game.model.enums.GameType;
import com.qijianguo.game.model.po.Task;
import com.qijianguo.game.model.vo.PeriodReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 任务
 * @author qijianguo
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    public void saveTask(Task task) {
        Task byGamePeriod = getByGamePeriod(task.getGameId(), task.getPeriod());
        if (byGamePeriod == null) {
            save(task);
        }
    }

    @Override
    public Task getByGamePeriod(int gameId, long period) {
        return lambdaQuery()
                .eq(Task::getGameId, gameId)
                .eq(Task::getPeriod, period)
                .one();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateResult(String gameType, Task task) {
        List<Integer> nums = getNums(gameType, task);
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

//    @Autowired
//    private BetStatService betStatService;

    private List<Integer> getNums(String gameType, Task task) {
        GameType match = GameType.match(gameType);
        if (match == null) {
            throw new IllegalArgumentException("game type not found, errGameType = " + gameType);
        }
        int length = -1;
        // 给出结果
        switch (match) {
            case _3D:
                length = 3 * 2;
                break;
            case _4D:
                length = 4 * 2;
                break;
            case _5D:
                length = 5 * 2;
                break;
            default:
        }

        /*List<Integer> nums = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            Integer randomNum = RandomUtils.secureRandomNum();
            nums.add(randomNum);
        }
        Collections.shuffle(nums);
        return nums.subList(0, length / 2);*/
        List<Integer> nums = RandomUtils.secureRandomNums(length);
        Collections.shuffle(nums);
        return nums.subList(0, length / 2);
    }

}
