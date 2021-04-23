package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yincheng.game.common.util.NumberUtils;
import com.yincheng.game.model.vo.BetAddReq;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;


    public BetHistory(User user, BetAddReq req, GameConfig betFee, GameConfig singleOdds, GameConfig numberOdds) {
        this.userId = user.getId();
        this.gameId = req.getGameId();
        this.period = req.getPeriod();
        this.target = req.getTarget();
        if (req.isNumBet()) {
            this.bet = StringUtils.join(req.getBetNums(), ",").toUpperCase();
            this.odds = numberOdds.getValue();
        } else {
            this.bet = StringUtils.join(req.getBetHloe(), ",").toUpperCase();
            this.odds = singleOdds.getValue();
        }
        this.fee = betFee.getValue();
        // 扣除手续费
        this.credit = (int) NumberUtils.keepPrecision(req.getCredit() - req.getCredit() * Double.parseDouble(this.fee), 0);
        this.result = "";
        this.status = 0;
        this.description = "";
        this.reward = 0;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }
}
