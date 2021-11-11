package com.qijianguo.game.model.vo;

import com.qijianguo.game.model.po.GameFlow;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel("游戏")
public class GameResp {

    @ApiModelProperty(value = "游戏ID", required = true, dataType = "Integer")
    private Integer id;
    @ApiModelProperty(value = "游戏名称", required = true, dataType = "Integer")
    private String name;
    @ApiModelProperty(value = "封面", required = true, dataType = "Integer")
    private String cover;
    @ApiModelProperty(value = "开启状态，1开启，0关闭", required = true, dataType = "Integer")
    private Integer enabled;

    public GameResp(GameFlow game) {
        if (game != null) {
            this.id = game.getId();
            this.name = game.getName();
            this.cover = game.getCover();
            this.enabled = game.getEnabled();
        }
    }
}
