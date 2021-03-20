package com.yincheng.game.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
public class PeriodResp {

    /** 游戏期数 */
    private Long period;
    /** 状态 */
    private Integer status;
    /** 开奖结果 */
    private String result;
    /** 总和 */
    private Integer sum;
    /** 开始时间 */
    private Date startTime;
    /** 结束时间 */
    private Date endTime;

}
