package com.xiushang.framework.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @ApiModelProperty(hidden = true)
    private String sort;
    @ApiModelProperty(hidden = true)
    private String dir;

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

    @ApiModelProperty(hidden = true)
    public int getOffset() {
        int pageNo = getPageNo() - 1;
        if (pageNo < 0) {
            pageNo = 0;
        }
        return pageNo * getPageSize();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Boolean getHasMore() {
        return getTotalCount()>(getPageSize()*getPageNo());
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public PageRequest createPageRequest() {
        Sort sort = null;
        if (StringUtils.isNotBlank(getSort())) {
            Sort.Direction direction = Sort.Direction.ASC;
            if ("desc".equals(getDir())) {
                direction = Sort.Direction.DESC;
            }
            sort = Sort.by(direction,getSort());
        }
        int page = getPageNo();
        if (page >= 1) {
            //分页从0开始
            page = page - 1;
        }
        if(sort==null){
            return PageRequest.of(page, getPageSize());
        }
        return PageRequest.of(page, getPageSize(), sort);
    }

    public PageRequest createPageRequest(Sort.Order... orders) {
        Sort sort = Sort.by(orders);

        int page = getPageNo();
        if (page >= 1) {
            //分页从0开始
            page = page - 1;
        }
        return PageRequest.of(page, getPageSize(), sort);
    }
}
