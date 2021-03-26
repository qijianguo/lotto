package com.yincheng.game.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author qijianguo
 */
@ApiModel("分页请求")
public class PageReq extends Page {

    @ApiModelProperty(value = "每页显示条数，默认 10", dataType = "Integer", example = "10")
    private long size = 10;
    @ApiModelProperty(value = "当前页", dataType = "Integer", example = "1")
    private long current = 1;
    @ApiModelProperty(value = "SQL 排序 ASC 数组", dataType = "List", example = "create_time")
    private List<String> ascs;
    @ApiModelProperty(value = "SQL 排序 DESC 数组", dataType = "List", example = "create_time")
    private List<String> descs;
    @ApiModelProperty(value = "是否返回总数，默认true", dataType = "boolean", example = "true")
    private boolean searchCount = true;

    public void setDefaultDescs(List<String> descs) {
        if (this.descs != null) {
            this.descs = descs;
        }
    }


    public void setDefaultAscs(List<String> ascs) {
        if (this.ascs != null) {
            this.ascs = ascs;
        }
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public Page setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return current;
    }

    @Override
    public Page setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public boolean isSearchCount() {
        return searchCount;
    }

    @Override
    public Page setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
        return this;
    }
}
