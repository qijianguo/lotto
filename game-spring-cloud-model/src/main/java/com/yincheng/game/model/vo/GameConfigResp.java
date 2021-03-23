package com.yincheng.game.model.vo;

import com.yincheng.game.model.po.GameConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel("游戏配置")
public class GameConfigResp {
    @ApiModelProperty(value = "配置字段名：", required = true, dataType = "String")
    private String name;
    @ApiModelProperty(value = "配置字段值", required = true, dataType = "String")
    private String value;
    @ApiModelProperty(value = "描述", required = true, dataType = "String")
    private String description;

    public GameConfigResp(GameConfig config) {
        if (config != null) {
            this.name = config.getName();
            this.value = config.getValue();
            this.description = config.getDescription();
        }
    }

}
