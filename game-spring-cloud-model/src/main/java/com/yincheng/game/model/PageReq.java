package com.yincheng.game.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author qijianguo
 */
@Data
@ApiModel("分页查询基类")
public class PageReq {

    @ApiModelProperty(value = "排序方式：ASC/DESC", dataType = "String", example = "ASC")
    private String sortBy;

    @ApiModelProperty(value = "排序字段", dataType = "String", example = "id")
    private String sortKeyword;

    @ApiModelProperty(value = "最后一条数据的ID", dataType = "Integer", example = "id", notes = "如果AES，则为最大的ID；如果是DES，则为最小的ID")
    private Integer latestId;

    @ApiModelProperty(value = "当前第x页", dataType = "Integer", example = "1")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示个数", dataType = "Integer", example = "10")
    private Integer size;

    /**
     * 是否是第一页
     * @return
     */
    public boolean isFirstPage() {
        if (pageNum == null || pageNum == 1) {
            return true;
        }
        return false;
    }

    /**
     * 初始化分页,如果前端未传
     * @param defaultSize
     */
    public void defPage(Integer defaultSize) {
        if (this.size == null || this.size == 0 || this.size > 50) {
            this.size = defaultSize == null ? 10 : defaultSize;
        }
        if (this.pageNum == null || this.pageNum == 0) {
            this.pageNum = 1;
        }
    }

    public void defSort(String sortKeyword, String sortBy) {
        if (StringUtils.isEmpty(this.sortKeyword)) {
            this.sortKeyword = sortKeyword;
        }
        if (StringUtils.isEmpty(this.sortBy)) {
            this.sortBy = sortBy;
        }
    }

}
