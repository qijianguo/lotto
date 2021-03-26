package com.yincheng.game.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel(value = "账户查询")
public class AccountDetailReq extends Page {

    @ApiModelProperty(value = "类型：1充值，2消费，3提现 4赠送，5中奖，-1 其他", required = true, dataType = "String")
    private Integer type;

    public boolean validate() {
        return type != null;
    }

}
