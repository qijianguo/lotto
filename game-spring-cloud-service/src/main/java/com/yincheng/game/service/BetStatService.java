package com.yincheng.game.service;

import com.yincheng.game.model.enums.Bet;
import com.yincheng.game.model.vo.BetAddReq;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qijianguo
 */
@Component
public class BetStatService {

    public volatile Map<String, Map<String, Integer>> statMap2 = new ConcurrentHashMap<>(16);

    public void add(BetAddReq req) {
        Set<Integer> nums = new HashSet<>();
        if (req.isNumBet()) {
            req.getBetNums().forEach(bet -> nums.add(bet));
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
            });
        }
        String periodKey = String.format("%d-%d", req.getGameId(), req.getPeriod());

        synchronized (this) {
            nums.forEach(num -> {
                String targetKey = req.getTarget().toUpperCase() + num;
                Map<String, Integer> targetMap;
                int count = 0;
                if (!statMap2.containsKey(periodKey)) {
                    targetMap = new ConcurrentHashMap<>(10);
                } else {
                    targetMap = statMap2.get(periodKey);
                    count = Optional.ofNullable(targetMap.get(targetKey)).orElse(0);
                }
                targetMap.put(targetKey, count + 1);
                statMap2.put(periodKey, targetMap);
                System.out.println(statMap2);
            });
        }

    }

    public String periodKey(Integer gameId, Long period) {
        return String.format("%d-%d", gameId, period);
    }

}
