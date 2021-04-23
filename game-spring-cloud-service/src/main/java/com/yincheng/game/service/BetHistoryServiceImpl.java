package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.common.util.ThreadPoolUtils;
import com.yincheng.game.dao.mapper.BetHistoryMapper;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.enums.Bet;
import com.yincheng.game.model.enums.Destination;
import com.yincheng.game.model.enums.NotificationType;
import com.yincheng.game.model.po.*;
import com.yincheng.game.model.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author qijianguo
 */
@Service
public class BetHistoryServiceImpl extends ServiceImpl<BetHistoryMapper, BetHistory> implements BetHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(BetHistoryServiceImpl.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private BetHistoryService betHistoryService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private GameConfigService gameConfigService;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private NotificationService notificationService;

    @Override
    public void settleAndNotice(String game, Task current, Task next) {
        RecentTaskResp resp = new RecentTaskResp(current, next);
        webSocketService.send(Destination.resultTopic(game), resp);
        if (current != null) {
            betHistoryService.settle(game, current, true);
        }
    }

    @Override
    public void settle(Task task) {
        if (task == null || task.getStatus() != 0 || StringUtils.isEmpty(task.getResult())) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        settle(null, task, false);
    }

    @Override
    public void settle(String gameName, Task task, boolean notice) {
        Date start = new Date();
        if (notice && StringUtils.isEmpty(gameName)) {
            throw new IllegalArgumentException("gameName mast be null or empty.");
        }
        List<Integer> result = Arrays.stream(task.getResult().split(",")).map(Integer::new).collect(Collectors.toList());
        List<BetHistory> list = lambdaQuery().eq(BetHistory::getGameId, task.getGameId())
                .eq(BetHistory::getPeriod, task.getPeriod())
                .eq(BetHistory::getStatus, 0)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        // 多线程执行
        CountDownLatch latch = new CountDownLatch(list.size());
        Map<Integer, List<BetHistory>> groupByUser = list.stream().collect(Collectors.groupingBy(BetHistory::getUserId));
        groupByUser.keySet().forEach(userId -> ThreadPoolUtils.execute(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
            List<BetHistory> userBetList = groupByUser.get(userId);
            Account account = settle(userBetList, result);
            if (notice && account != null) {
                webSocketService.send(String.valueOf(account.getUserId()), Destination.accountQueue(), new RewardResponse(account));
                User user = notificationService.reward(NotificationReq.create(gameName.toUpperCase(), account, NotificationType.REWARD));
                webSocketService.send(Destination.rewardTopic(gameName.toLowerCase()), NoticeResp.init(user, account));
            }
            latch.countDown();
        }));
        /*list.forEach(bet -> ThreadPoolUtils.execute(() -> {
            Account account = betHistoryService.settle(bet, result);
            if (notice && account != null) {
                webSocketService.send(String.valueOf(account.getUserId()), Destination.accountQueue(), new RewardResponse(account));
                User user = notificationService.reward(new NotificationReq(account.getUserId(), "Rp" + account.getReward() + " in " + gameName.toUpperCase()));
                webSocketService.send(Destination.rewardTopic(gameName.toLowerCase()), NoticeResp.init(user, account));
            }
            latch.countDown();
        }));*/
        logger.info("{} --> {} settle finished! time：{}", gameName, task, (System.currentTimeMillis() - start.getTime()) / 1000);
    }

    private Account settle(List<BetHistory> bets, List<Integer> result) {
        AtomicReference<Account> account = new AtomicReference<>(null);
        bets.forEach(bet -> {
            Account acc = settle(bet, result);
            if (acc != null) {
                Account last = account.get();
                if (last != null) {
                    acc.setCredit(last.getCredit() + acc.getCredit());
                }
                account.set(acc);
            }
        });
        return account.get();
    }

    /*private Account settle(BetHistory bet, List<Integer> result, Integer maxTryTimes) {
        Account acc = null;
        try {
            acc = betHistoryService.settle(bet, result);
        } catch (Exception e) {
            logger.warn("try settle {}-{}-{} times = {} ", bet.getUserId(), bet.getGameId(), bet.getPeriod(), maxTryTimes);
            try {
                Thread.sleep(500);
            } catch (InterruptedException interruptedException) {
                logger.error(interruptedException.getMessage(), interruptedException);
            }
            if (maxTryTimes >= 0) {
                settle(bet, result, maxTryTimes++);
            }
        }
        return acc;
    }*/

    /**
     * 计算结果
     * @param betHistory 下注记录
     * @param result 开奖结果
     * @return 用户账户积分
     */
    @Override
    public Account settle(BetHistory betHistory, List<Integer> result) {
        // 标的
        Bet.Target t = Bet.target().get(betHistory.getTarget().toUpperCase());
        // 开奖结果
        Integer num = Bet.getTargetNum(t, result).get(0);
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
            } catch (NumberFormatException ignore) {
                // 非数字
                Bet.Mode mode1 = Bet.mode().get(i.toUpperCase());
                if (mode1 == null) {
                    return;
                }
                double middle = 4.5;
                if (t == Bet.Target.SUM) {
                    middle = 13.5;
                }
                switch (mode1) {
                    case HIGH:
                        if (num > middle) {
                            // 中奖
                            totalCount.incrementAndGet();
                        }
                        break;
                    case LOW:
                        if (num < middle) {
                            // 中奖
                            totalCount.incrementAndGet();
                        }
                        break;
                    case ODD:
                        if (num % 2 == 1) {
                            // 中奖
                            totalCount.incrementAndGet();
                        }
                        break;
                    case EVEN:
                        if (num % 2 == 0) {
                            // 中奖
                            totalCount.incrementAndGet();
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("game-mode not found: " + mode1);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
        });

        return betHistoryService.saveInDb(betHistory, totalCount.get(), betList.size(), num);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Account saveInDb(BetHistory betHistory, Integer rewardCount, Integer betSize, Integer singleResult) {
        int reward = 0;
        String description;
        if (rewardCount > 0) {
            int credit = betHistory.getCredit();
            reward = (int) (credit / betSize * rewardCount * Double.parseDouble(betHistory.getOdds()));
            description = "win";
        } else {
            description = "loss";
        }
        boolean updated = lambdaUpdate().eq(BetHistory::getId, betHistory.getId()).eq(BetHistory::getStatus, 0)
                .set(BetHistory::getResult, String.valueOf(singleResult))
                .set(BetHistory::getStatus, 1)
                .set(BetHistory::getUpdateTime, new Date())
                .set(BetHistory::getReward, reward)
                .set(BetHistory::getDescription, description)
                .update();
        String lockKey = USER_ACCOUNT_LOCK + betHistory.getUserId();
        String value = UUID.randomUUID().toString();
        if (rewardCount > 0 && updated) {
            boolean success = redisLockHelper.lock(lockKey, value, 2, TimeUnit.SECONDS);
            while (!success) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    logger.error(interruptedException.getMessage(), interruptedException);
                    throw new BusinessException(EmBusinessError.SYSTEM_BUSY);
                }
                success = redisLockHelper.lock(lockKey, value, 2, TimeUnit.SECONDS);
            }
            try {
                // 更新账户余额
                AccountDetail detail = AccountDetail.create(betHistory.getUserId(), reward, AccountDetailType.REWARD);
                Account account = accountService.betReward(detail);
                account.setCredit(reward);
                return account;
            } finally {
                redisLockHelper.unlock(lockKey, value);
            }
        }
        return null;
    }

    @Autowired
    private BetStatService betStatService;
    @Autowired
    private RedisLockHelper redisLockHelper;
    private static final String USER_ACCOUNT_LOCK = "user_account:";

    @Override
    public Account bet(User user, BetAddReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        List<GameConfig> list = gameConfigService.list();
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(EmBusinessError.GAME_CONFIG_NOT_FOUND);
        }
        Map<String, GameConfig> collect = list.stream().collect(Collectors.toMap(GameConfig::getName, Function.identity()));
        GameConfig min = collect.get("bet_amount_min");
        GameConfig max = collect.get("bet_amount_max");
        if (min == null || max == null) {
            throw new BusinessException(EmBusinessError.GAME_CONFIG_NOT_FOUND);
        }
        if (req.getCredit() < Integer.parseInt(min.getValue()) || req.getCredit() > Integer.parseInt(max.getValue())) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        GameConfig betFee = collect.get("bet_fee");
        GameConfig singleOdds = collect.get("high_low_odd_even_odds");
        GameConfig numberOdds = collect.get("number_odds");
        // 查看开奖状态，如果是已开奖则不能下注, 不在时间段内不能下注
        Task period = taskService.getByGamePeriod(req.getGameId(), req.getPeriod());
        Date now = new Date();
        if (period == null || period.getStatus() == 1 || now.before(period.getStartTime()) || now.after(period.getEndTime())) {
            throw new BusinessException(EmBusinessError.PERIOD_DRAWN);
        }

        BetHistory betHistory = new BetHistory(user, req, betFee, singleOdds, numberOdds);
        betHistoryService.save(betHistory);

        // 更新账户余额
        int fee = req.getCredit() - betHistory.getCredit();
        AccountDetail detail = AccountDetail.createWithFee(user.getId(), req.getCredit(), fee, AccountDetailType.SPEED);
        Account account = accountService.betSpeed(detail);

        // TODO 下注统计
        //betStatService.add(req);

        return account;
    }

    @Override
    public IPage<BetHistory> list(User user, BetReq req) {
        if (!req.validate()) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        return lambdaQuery().eq(BetHistory::getUserId, user.getId())
                .eq(BetHistory::getGameId, req.getGameId())
                .eq(req.getPeriod() != null, BetHistory::getPeriod, req.getPeriod())
                .eq(!StringUtils.isEmpty(req.getTarget()), BetHistory::getTarget, req.getTarget())
                .page(req);
    }

}
