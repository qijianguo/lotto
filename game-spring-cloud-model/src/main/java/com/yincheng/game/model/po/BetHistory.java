package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yincheng.game.model.vo.BetAddReq;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;

/**
 * 游戏配置
 * @author qijianguo
 */
@TableName("t_bet_history")
@Data
@NoArgsConstructor
public class BetHistory {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 用户ID */
    private Integer userId;
    /** 游戏ID */
    private Integer gameId;
    /** 期号 */
    private Long period;
    /** 标的 */
    private String target;
    /** 下注 */
    private String bet;
    /** 下注积分 */
    private Integer credit;
    /** 开奖结果 */
    private String result;
    /** 状态：0未开奖，1已开奖 */
    private Integer status;
    /** 描述 */
    private String description;
    /** 奖励金额 */
    private Integer reward;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;


    public BetHistory(User user, BetAddReq req) {
        this.userId = user.getId();
        this.gameId = req.getGameId();
        this.period = req.getPeriod();
        this.target = req.getTarget();
        if (CollectionUtils.isEmpty(req.getBetHlov())) {
            this.bet = StringUtils.join(req.getBetNums(), ",").toUpperCase();
        } else {
            this.bet = StringUtils.join(req.getBetHlov(), ",").toUpperCase();
        }
        this.credit = req.getCredit();
        this.result = "";
        this.status = 0;
        this.description = "";
        this.reward = 0;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }
}
