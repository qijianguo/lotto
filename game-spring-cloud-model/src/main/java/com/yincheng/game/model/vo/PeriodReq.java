package com.yincheng.game.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
@ApiModel("期表查询")
public class PeriodReq extends Page {

    @ApiModelProperty(value = "游戏ID", required = true, dataType = "Integer")
    private Integer gameId;
    @ApiModelProperty(value = "状态", required = true, dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "在...之前", required = true, dataType = "Date")
    private Date beforeDate;
    @ApiModelProperty(value = "在...之后", required = true, dataType = "Date")
    private Date afterDate;

    public boolean validate() {
        long size = getSize();
        int max = 100;
        if (size > max) {
            setSize(max);
        }
        return gameId != null;
    }

    public static PeriodReq create(Integer gameId, Integer status) {
        PeriodReq req = new PeriodReq();
        req.setGameId(gameId);
        req.setStatus(status);
        return req;
    }

}
