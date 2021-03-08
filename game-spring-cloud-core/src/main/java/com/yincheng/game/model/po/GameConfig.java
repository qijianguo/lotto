package com.yincheng.game.model.po;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 游戏配置
 * @author qijianguo
 */
@TableName("t_game_config")
public class GameConfig {

    private Integer id;

    private Integer gameId;

    private String name;

    private String rateField;

    private String rate;

    private Integer multi;

    private Integer priority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRateField() {
        return rateField;
    }

    public void setRateField(String rateField) {
        this.rateField = rateField;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Integer getMulti() {
        return multi;
    }

    public void setMulti(Integer multi) {
        this.multi = multi;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
