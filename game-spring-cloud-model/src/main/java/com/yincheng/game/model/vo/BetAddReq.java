package com.yincheng.game.model.vo;

import com.yincheng.game.model.enums.Bet;
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
        if (CollectionUtils.isEmpty(betNums)) {
            if (!CollectionUtils.isEmpty(betHloe) && credit % betHloe.size() == 0) {
                betHloe.forEach(mode -> {
                    if (!Bet.mode().containsKey(mode.toUpperCase())) {
                        bet.set(false);
                        return;
                    }
                });
            }
        } else {
            if (credit % betNums.size() == 0 && CollectionUtils.isEmpty(betHloe)) {
                betNums.forEach(num -> {
                    if (num < 0 || num > 9) {
                        bet.set(false);
                        return;
                    }
                });
                bet.set(isNumBet = true);
            }
        }
        return base && bet.get();
    }



}
