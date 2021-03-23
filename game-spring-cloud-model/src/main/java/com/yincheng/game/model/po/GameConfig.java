package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yincheng.game.model.vo.GameConfigResp;
import lombok.Data;

import java.util.Date;

/**
 * 配置
 * @author qijianguo
 */
@TableName("t_game_config")
@Data
public class GameConfig {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String value;

    private String description;

    private Date createTime;

    private Date updateTime;

}
