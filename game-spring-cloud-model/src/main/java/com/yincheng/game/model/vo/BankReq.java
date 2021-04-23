package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author qijianguo
 */
@ApiModel("添加/修改银行卡信息")
@Data
public class BankReq {
    @ApiModelProperty(value = "记录ID,如果有值，则修改，否则添加", dataType = "Integer")
    private Integer id;
    @ApiModelProperty(value = "银行名称：如中国银行", required = true, dataType = "String")
    private String bank;
    @ApiModelProperty(value = "支行名称：如普陀分行", required = true, dataType = "String")
    private String branch;
    @ApiModelProperty(value = "银行卡号", required = true, dataType = "String")
    private String cardNo;
    @ApiModelProperty(value = "收款人姓名", required = true, dataType = "String")
    private String realName;

    public boolean validate() {
        return !StringUtils.isEmpty(bank) && !StringUtils.isEmpty(branch) && !StringUtils.isEmpty(cardNo) && !StringUtils.isEmpty(realName);
    }

}
