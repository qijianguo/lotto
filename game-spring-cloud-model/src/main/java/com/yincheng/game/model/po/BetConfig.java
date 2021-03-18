package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 下注配置
 * @author qijianguo
 */
@Data
public class BetConfig {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer gameId;

    private String target;

    private Integer high;

    private Integer low;

    private Integer odd;

    private Integer even;

    private Integer num;

    private Integer highSum;

    private Integer lowSum;

    private Integer oddSum;

    private Integer evenSum;
}
