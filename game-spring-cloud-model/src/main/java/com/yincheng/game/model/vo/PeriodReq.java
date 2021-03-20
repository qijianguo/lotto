package com.yincheng.game.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel("期表查询")
public class PeriodReq extends Page {

    @ApiModelProperty(value = "游戏ID", required = true, dataType = "Integer")
    private Integer gameId;

    public boolean validate() {
        return gameId != null;
    }

}
