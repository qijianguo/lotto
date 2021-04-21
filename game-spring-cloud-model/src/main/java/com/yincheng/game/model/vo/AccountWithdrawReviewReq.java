package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 * 提现审核
 */
@Data
@ApiModel("提现审核请求参数")
public class AccountWithdrawReviewReq {

    @ApiModelProperty(value = "提现记录ID", required = true, dataType = "Integer", example = "0")
    private Integer accountDetailId;
    @ApiModelProperty(value = "是否通过：0否，1是", required = true, dataType = "Integer", example = "0")
    private Integer pass;
    @ApiModelProperty(value = "说明", dataType = "String", example = "pass")
    private String description;

    public boolean validate() {
        return accountDetailId != null && pass != null && (description == null || description.length() < 50);
    }

}
