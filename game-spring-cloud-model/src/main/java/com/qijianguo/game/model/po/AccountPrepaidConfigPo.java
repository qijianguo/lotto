package com.qijianguo.game.model.po;

import lombok.Data;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
public class AccountPrepaidConfigPo {

    private Integer id;

    private Integer amount;

    private Integer credit;

    private Integer extCredit;

    private Integer discount;

    private String tag;

    private Date createTime;

    private Date updateTime;

    private Integer active;

}
