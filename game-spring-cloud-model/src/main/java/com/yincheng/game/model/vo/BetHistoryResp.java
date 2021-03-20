package com.yincheng.game.model.vo;

import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class BetHistoryResp {

    /** 期号 */
    private Long period;
    /** 标的 */
    private String target;
    /** 下注 */
    private String bet;
    /** 下注积分 */
    private Integer credit;
    /** 赔率 */
    private String odds;
    /** 费率 */
    private String fee;
    /** 开奖结果 */
    private String result;
    /** 状态：0未开奖，1已开奖 */
    private Integer status;
    /** 描述 */
    private String description;
    /** 奖励金额 */
    private Integer reward;


}
