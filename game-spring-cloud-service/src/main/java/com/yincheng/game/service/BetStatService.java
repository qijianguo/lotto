package com.yincheng.game.service;

import com.yincheng.game.common.util.Lfu;
import com.yincheng.game.common.util.Node;
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

    private volatile Map<String, Lfu> map = new ConcurrentHashMap(3);

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
