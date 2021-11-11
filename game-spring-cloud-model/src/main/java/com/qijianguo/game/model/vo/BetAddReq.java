package com.qijianguo.game.model.vo;

import com.qijianguo.game.model.enums.Bet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author qijianguo
 */
@Data
@ApiModel(value = "下注")
public class BetAddReq {

    @ApiModelProperty(value = "游戏ID", required = true, dataType = "Integer")
    private Integer gameId;
    @ApiModelProperty(value = "期号", required = true, dataType = "Integer")
    private Long period;
    @ApiModelProperty(value = "标的,例如A，B，C，SUM", required = true, dataType = "String")
    private String target;
    @ApiModelProperty(value = "下注数字", required = true, dataType = "String")
    private List<Integer> betNums;
    @ApiModelProperty(value = "下注区间", required = true, dataType = "String")
    private List<String> betHloe;
    @ApiModelProperty(value = "积分", dataType = "Integer")
    private Integer credit;
    @ApiModelProperty(value = "是否是数字", dataType = "Integer", hidden = true)
    private boolean isNumBet = false;

    public boolean validate() {
        boolean base = gameId != null && period != null && target != null && credit != null && credit >= 2000;
        AtomicBoolean bet = new AtomicBoolean(true);
        // 数字和其他类型是二选一
        if (!CollectionUtils.isEmpty(betHloe)) {
            if (!CollectionUtils.isEmpty(betNums) || credit % (betHloe.size() * 2000) != 0) {
                bet.set(false);
            } else {
                betHloe.forEach(mode -> {
                    if (!Bet.mode().containsKey(mode.toUpperCase())) {
                        bet.set(false);
                    }
                });
            }
        } else {
            if (credit % (betNums.size() * 2000) != 0) {
                bet.set(false);
            } else {
                betNums.forEach(num -> {
                    if (num < 0 || num > 9) {
                        bet.set(false);
                    }
                });
            }
            isNumBet = bet.get();
        }
        return base && bet.get();
    }



}
