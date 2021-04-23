package com.yincheng.game.service;

import com.yincheng.game.common.util.Lfu;
import com.yincheng.game.common.util.Node;
import com.yincheng.game.model.enums.Bet;
import com.yincheng.game.model.vo.BetAddReq;
import com.yincheng.game.model.vo.GameStatResp;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qijianguo
 */
@Component
public class BetStatService {

    private volatile Map<String, Lfu> map = new ConcurrentHashMap(3);

    private volatile Map<String, Map<String, List<Integer>>> map2 = new ConcurrentHashMap<>();

    // Map<gameId-Period, Map<target,Map<number, credit>
    private Map<String, Map<String, Map<Integer, Integer>>> map3 = new ConcurrentHashMap();

    public void add3(BetAddReq req) {
        if (req.getTarget().equalsIgnoreCase("SUM")) {
            return;
        }
        List<Integer> nums = new ArrayList<>();
        if (req.isNumBet()) {
            nums.addAll(req.getBetNums());
        } else {
            List<Integer> list = new ArrayList<>();
            req.getBetHloe().forEach(bet -> {
                switch (Bet.Mode.valueOf(bet.toUpperCase())) {
                    case HIGH:
                        list.addAll(Arrays.asList(5,6,7,8,9));
                        break;
                    case EVEN:
                        list.addAll(Arrays.asList(0,2,4,6,8));
                        break;
                    case ODD:
                        list.addAll(Arrays.asList(1,3,5,7,9));
                        break;
                    case LOW:
                        list.addAll(Arrays.asList(0,1,2,3,4));
                        break;
                }
                nums.addAll(list);
            });
        }
        Integer credit = req.getCredit() / nums.size();

        String gamePeriodKey = String.format("%d%d", req.getGameId(), req.getPeriod());
        Map<String, Map<Integer, Integer>> targetMap = map3.get(gamePeriodKey);
        if (targetMap == null) {
            Map<Integer, Integer> targetCreditMap = new ConcurrentHashMap<>();
            nums.forEach(num -> targetCreditMap.put(num, credit));
            targetMap = new HashMap<>();
            targetMap.put(gamePeriodKey, targetCreditMap);
        } else {
            Map<Integer, Integer> betMap = targetMap.get(req.getTarget());
            if (betMap == null) {
                Map<Integer, Integer> targetCreditMap = new ConcurrentHashMap<>();
                nums.forEach(num -> targetCreditMap.put(num, credit));
                targetMap.put(gamePeriodKey, targetCreditMap);
            } else {
                nums.forEach(num -> {
                    Integer betCredit = betMap.getOrDefault(num, 0);
                    betMap.put(num, credit + betCredit);
                });
            }
        }
        List<GameStatResp> gameStatResps = get(req.getGameId(), req.getPeriod(), 3);
        System.out.println("=====" +gameStatResps);
    }

    public List<GameStatResp> get(Integer gameId, Long period, Integer size) {
        String gamePeriodKey = String.format("%d%d", gameId, period);
        Map<String, Map<Integer, Integer>> stringMapMap = map3.get(gamePeriodKey);
        List<GameStatResp> result = new ArrayList<>(size);
        if (stringMapMap != null) {
            stringMapMap.keySet().forEach(target -> {
                Map<Integer, Integer> numberCreditMap = stringMapMap.get(target);
                GameStatResp resp = GameStatResp.create(numberCreditMap);
                // 标的
                Bet.Target t = Bet.target().get(target.toUpperCase());
                // 开奖结果
                switch (t) {
                    case A:
                        result.add(0, resp);
                        break;
                    case B:
                        result.add(1, resp);
                        break;
                    case C:
                        result.add(2, resp);
                        break;
                    case D:
                        result.add(3, resp);
                        break;
                }
            });
        }

        return result;
    }

    /**
     * 获取游戏的下注
     * @param gameId
     * @param period
     * @return
     */
    public Map<String, List<Integer>> getGameBet(Integer gameId, Long period) {
        String gamePeriodKey = String.format("%d%d", gameId, period);
        Map<String, List<Integer>> stringListMap = map2.get(gamePeriodKey);
        return stringListMap;
    }

    public void add2(BetAddReq req) {
        List<Integer> nums = new ArrayList<>();
        if (req.isNumBet()) {
            nums.addAll(req.getBetNums());
        } else {
            List<Integer> list = new ArrayList<>();
            req.getBetHloe().forEach(bet -> {
                switch (Bet.Mode.valueOf(bet.toUpperCase())) {
                    case HIGH:
                        list.addAll(Arrays.asList(5,6,7,8,9));
                        break;
                    case EVEN:
                        list.addAll(Arrays.asList(0,2,4,6,8));
                        break;
                    case ODD:
                        list.addAll(Arrays.asList(1,3,5,7,9));
                        break;
                    case LOW:
                        list.addAll(Arrays.asList(0,1,2,3,4));
                        break;
                }
                nums.addAll(list);
            });
        }
        String gamePeriodKey = String.format("%d%d", req.getGameId(), req.getPeriod());
        String target = req.getTarget();
        if (!map2.containsKey(gamePeriodKey)) {
            Map<String, List<Integer>> m = new ConcurrentHashMap<>();
            m.put(target, nums);
            map2.put(gamePeriodKey, m);
        } else {
            Map<String, List<Integer>> stringListMap = map2.get(gamePeriodKey);
            List<Integer> betList = stringListMap.get(req.getTarget());
            if (CollectionUtils.isEmpty(betList)) {
                stringListMap.put(target, nums);
            } else {
                betList.addAll(nums);
                stringListMap.put(target, betList);
            }
        }
    }

    public void add(BetAddReq req) {
        List<Integer> nums = new ArrayList<>();
        if (req.isNumBet()) {
            nums.addAll(req.getBetNums());
        } else {
            List<Integer> list = new ArrayList<>();
            req.getBetHloe().forEach(bet -> {
                switch (Bet.Mode.valueOf(bet.toUpperCase())) {
                    case HIGH:
                        list.addAll(Arrays.asList(5,6,7,8,9));
                        break;
                    case EVEN:
                        list.addAll(Arrays.asList(0,2,4,6,8));
                        break;
                    case ODD:
                        list.addAll(Arrays.asList(1,3,5,7,9));
                        break;
                    case LOW:
                        list.addAll(Arrays.asList(0,1,2,3,4));
                        break;
                }
                nums.addAll(list);
            });
        }
        String key = key(req.getGameId(), req.getPeriod(), req.getTarget());
        Lfu lfu = map.get(key);
        if (lfu == null) {
            lfu = new Lfu(10);
            map.put(key, lfu);
        }
        for (Integer num : nums) {
            lfu.put(req.getTarget() + num, num);
        }
    }

    public Integer getMaxNum(Integer gameId, Long period, String target) {
        String key = key(gameId, period, target);
        Lfu lfu = map.get(key);
        if (lfu == null) {
            return -1;
        }
        Node min = lfu.getMinCountNode();
        Node max = lfu.getMaxCountNode();
        int i = Integer.parseInt(String.valueOf(min.getValue()));
        int j = Integer.parseInt(String.valueOf(max.getValue()));
        if (i == j) {
            return -1;
        }
        return j;
    }

    public String key(Integer gameId, Long period, String target) {
        return gameId + (period + target);
    }

}
