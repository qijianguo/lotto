package com.yincheng.game.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author qijianguo
 */
@Data
public class BaseReq {

    @ApiModelProperty(value = "每页显示条数，默认 10", dataType = "Integer")
    private long size = 10;
    @ApiModelProperty(value = "当前页", dataType = "Integer")
    private long current = 1;
    @ApiModelProperty(value = "SQL 排序 ASC 数组", dataType = "List")
    private List<String> ascs;
    @ApiModelProperty(value = "SQL 排序 DESC 数组", dataType = "List")
    private List<String> descs;
    @ApiModelProperty(value = "是否返回总数，默认true", dataType = "boolean")
    private boolean searchCount = true;
}
