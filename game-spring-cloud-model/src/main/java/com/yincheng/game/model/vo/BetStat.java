package com.yincheng.game.model.vo;

import com.yincheng.game.model.po.BetHistory;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qijianguo
 */
@Data
public class BetStat {

    private Integer numName;

    private Integer numCount;

    private Integer totalCount;


    /* @Data
    public static class Bet {

    }

    public BetStat convert(String target, List<Integer> numbers) {
        BetStat betStat = new BetStat();
        betStat.setTarget(target);
        numbers.forEach(num -> {
            Bet test = new Bet(num, 1);
            betList.add(test);
        });
        betStat.setBetList(betList);
        return betStat;
    }

    public void add(String target, List<Integer> bets) {
        if (!CollectionUtils.isEmpty(this.betList)) {
            this.betList.forEach(bet -> {
                if (bets.contains(bet)) {
                    bet.count += 1;
                }
            });
        }
    }*/

}
