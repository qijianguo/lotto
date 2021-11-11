package com.qijianguo.game.model.vo;

import com.qijianguo.game.model.po.UserBank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel("银行卡信息")
public class BankResp {
    @ApiModelProperty(value = "记录ID", required = true, dataType = "Integer", example = "1")
    private Integer id;
    @ApiModelProperty(value = "银行名称：如中国银行", required = true, dataType = "String")
    private String bank;
    @ApiModelProperty(value = "支行名称：如普陀分行", required = true, dataType = "String")
    private String branch;
    @ApiModelProperty(value = "银行卡号", required = true, dataType = "String")
    private String cardNo;
    @ApiModelProperty(value = "收款人姓名", required = true, dataType = "String")
    private String realName;

    public static BankResp create(UserBank userBank) {
        BankResp resp = new BankResp();
        if (userBank != null) {
            resp.setId(userBank.getId());
            resp.setBank(userBank.getBank());
            resp.setBranch(userBank.getBranch());
            resp.setCardNo(userBank.getCardNo());
            resp.setRealName(userBank.getRealName());
        }
        return resp;
    }

}
