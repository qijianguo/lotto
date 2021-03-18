package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.dao.mapper.BetHistoryMapper;
import com.yincheng.game.model.enums.Bet;
import com.yincheng.game.model.po.*;
import com.yincheng.game.model.vo.BetAddReq;
import com.yincheng.game.model.vo.BetReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author qijianguo
 */
@Service
public class BetHistoryServiceImpl extends ServiceImpl<BetHistoryMapper, BetHistory> implements BetHistoryService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private BetHistoryService betHistoryService;
    @Autowired
    private AccountService accountService;

    private static final Double MIDDLE = 4.5;

    private static final Integer MOD_0 = 0;
    private static final Integer MOD_1 = 1;
    private static final Integer N_2 = 2;

    @Override
    public void settle(Task task) {
        settle(task, false);
    }

    @Override
    public void settle(Task task, boolean notice) {
        List<Integer> result = Arrays.stream(task.getResult().split(",")).map(Integer::new).collect(Collectors.toList());
        List<BetHistory> list = lambdaQuery().eq(BetHistory::getGameId, task.getGameId())
                .eq(BetHistory::getPeriod, task.getPeriod())
                .eq(BetHistory::getStatus, 0)
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        // 多线程执行
        list.forEach(single -> {
            settle(single, result);
            // TODO 通知开奖结果， 放到消息队列

        });

    }

    /**
     * 计算结果
     * @param betHistory 下注记录
     * @param result 开奖结果
     */
    @Override
    public void settle(BetHistory betHistory, List<Integer> result) {
        // 标的
        Bet t = Bet.target().get(betHistory.getTarget().toUpperCase());
        // 开奖结果
        Integer num = getNum(t, result);
        List<String> betList = Arrays.stream(betHistory.getBet().split(",")).collect(Collectors.toList());
        AtomicInteger totalCount = new AtomicInteger(0);
        betList.forEach(i -> {
            try {
                // 数字
                int i1 = Integer.parseInt(i);
                if (num == i1) {
                    // 中奖
                    totalCount.incrementAndGet();
                }
            } catch (Exception ignore) {
                // 非数字
                Bet.Mode mode1 = Bet.mode().get(i.toUpperCase());
                if (mode1 == null) {
                    return;
                }
                switch (mode1) {
                    case HIGH:
                        if (num > MIDDLE) {
                            // 中奖
                            totalCount.incrementAndGet();
                        }
                        break;
                    case LOW:
                        if (num < MIDDLE) {
                            // 中奖
                            totalCount.incrementAndGet();
                        }
                        break;
                    case ODD:
                        if (num % N_2 == MOD_1) {
                            // 中奖
                            totalCount.incrementAndGet();
                        }
                        break;
                    case EVEN:
                        if (num % N_2 == MOD_0) {
                            // 中奖
                            totalCount.incrementAndGet();
                        }
                        break;
                    default:
                }
            }
        });
        int reward = 0;
        String description;
        if (totalCount.get() > 0) {
            reward = betHistory.getCredit() / betList.size() * totalCount.get();
            description = "win";
        } else {
            description = "loss";
        }
        lambdaUpdate().eq(BetHistory::getId, betHistory.getId()).eq(BetHistory::getStatus, 0)
                .set(BetHistory::getResult, String.valueOf(num))
                .set(BetHistory::getStatus, 1)
                .set(BetHistory::getUpdateTime, new Date())
                .set(BetHistory::getReward, reward)
                .set(BetHistory::getDescription, description)
                .update();
    }

    private Integer getNum(Bet t, List<Integer> result) {
        Integer num = -1;
        switch (t) {
            case A:
                num = result.size() > 1 ? result.get(0) : -1;
                break;
            case B:
                num = result.size() > 2 ? result.get(1) : -1;
                break;
            case C:
                num = result.size() > 3 ? result.get(2) : -1;
                break;
            case D:
                num = result.size() > 4 ? result.get(3) : -1;
                break;
            case SUM:
                break;
            default:
        }
        return num;
    }

    @Override
    public Account bet(User user, BetAddReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        // 查看开奖状态，如果是已开奖则不能下注
        Task period = taskService.getByGamePeriod(req.getGameId(), req.getPeriod());
        if (period == null || period.getStatus() == 1) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        // 更新账户余额
        Account account = accountService.speed(user, req.getCredit());

        betHistoryService.save(new BetHistory(user, req));
        return account;
    }

    @Override
    public List<BetHistory> list(User user, BetReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        return lambdaQuery().eq(BetHistory::getGameId, req.getGameId())
                .eq(BetHistory::getUserId, user.getId())
                .eq(BetHistory::getPeriod, req.getPeriod())
                .eq(BetHistory::getTarget, req.getTarget())
                .list();
    }

}
