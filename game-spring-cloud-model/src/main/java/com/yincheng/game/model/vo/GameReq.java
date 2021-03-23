package com.yincheng.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@ApiModel(value = "游戏保存或修改")
public class GameReq {

    @ApiModelProperty(value = "游戏ID", required = true, dataType = "Integer")
    private Integer id;
    @ApiModelProperty(value = "类型：3d/4d/5d", dataType = "String")
    private String type;
    @ApiModelProperty(value = "类型：3D/4D/5D",  dataType = "String")
    private String name;
    @ApiModelProperty(value = "展示图片",  dataType = "String")
    private String cover;
    @ApiModelProperty(value = "描述",  dataType = "String")
    private String description;
    @ApiModelProperty(value = "跳转地址",  dataType = "String")
    private String link;
    @ApiModelProperty(value = "状态,1开启，0停止",  dataType = "Integer")
    private Integer enabled;
    @ApiModelProperty(value = "执行周期表达式",  dataType = "String")
    private String cron;
    @ApiModelProperty(value = "当前期数",  dataType = "Long")
    private Long period;
    @ApiModelProperty(value = "开奖结果",  dataType = "Long")
    private String result;
    @ApiModelProperty(value = "下一期号",  dataType = "Long")
    private Long nextPeriod;

}
