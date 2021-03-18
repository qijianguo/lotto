package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
    private List<String> betHlov;
    @ApiModelProperty(value = "积分", dataType = "Integer")
    private Integer credit;

    public boolean validate() {
        boolean base = gameId != null && period != null && target != null && credit != null && credit > 2000;
        boolean bet = false;
        // 数字和其他类型是二选一
        if (CollectionUtils.isEmpty(betNums)) {
            if (!CollectionUtils.isEmpty(betHlov) && credit % betHlov.size() == 0) {
                bet = true;
            }
        } else {
            if (credit % betNums.size() == 0 && CollectionUtils.isEmpty(betHlov)) {
                bet = true;
            }
        }
        return base && bet;
    }



}
