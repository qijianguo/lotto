package com.yincheng.game.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qijianguo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页查询结果基类")
public class PageResp<T> {

    @ApiModelProperty(value = "当前页码", dataType = "Integer")
    private int pageNum;
    @ApiModelProperty(value = "当前页个数", dataType = "Integer")
    private int size;
    @ApiModelProperty(value = "总个数", dataType = "Long")
    private long totalSize;
    @ApiModelProperty(value = "总页数", dataType = "Long")
    private long totalPage;
    @ApiModelProperty(value = "数据", dataType = "List")
    private T data;

    public PageResp(T data) {
        this.data = data;
    }

    public PageResp(int pageNum, int size, long totalSize, T data) {
        this.pageNum = pageNum;
        this.size = size;
        this.totalSize = totalSize;
        this.data = data;
        if (totalSize % size > 0) { // 100 / 15  6
            this.totalPage = totalSize / size + 1;
        } else {
            this.totalPage = totalSize / size;
        }
    }

}
