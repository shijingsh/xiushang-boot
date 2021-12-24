package com.xiushang.framework.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分页查询抽象类
 */
public abstract class MgPageable implements Serializable {

    /**
     * 当前的页数
     */
    @ApiModelProperty(notes = "当前的页数")
    private int pageNo = 1;

    /**
     * 当前每页所显示的行数。
     */
    @ApiModelProperty(notes = "当前每页所显示的行数")
    private int pageSize = 15;
    /**
     * 总行数。
     */
    @ApiModelProperty(notes = "总行数")
    private long totalCount = 0;

    @ApiModelProperty(notes = "是否有更多")
    private Boolean hasMore = false;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }



    public Boolean getHasMore() {
        return getTotalCount()>(getPageSize()*getPageNo());
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

}
