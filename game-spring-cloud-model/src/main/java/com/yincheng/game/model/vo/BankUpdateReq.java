package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author qijianguo
 */
@ApiModel("银行请求")
@Data
public class BankUpdateReq {
    @ApiModelProperty(value = "记录ID", required = true, dataType = "Integer")
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
        return id != null && (bank != null || branch != null || cardNo != null || realName != null);
    }

}
