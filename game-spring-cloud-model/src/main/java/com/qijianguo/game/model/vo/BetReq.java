package com.qijianguo.game.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author qijianguo
 */
@ApiModel(value = "下注")
@Data
public class BetReq extends Page {

    @ApiModelProperty(value = "游戏ID", required = true, dataType = "Integer")
    private Integer gameId;
    @ApiModelProperty(value = "期号", dataType = "Integer")
    private Long period;
    @ApiModelProperty(value = "标的,例如A，B，C，SUM", dataType = "String")
    private String target;

    public boolean validate() {
        if (descs() == null) {
            setDesc("period", "create_time");
        }
        return gameId != null;
    }

}
