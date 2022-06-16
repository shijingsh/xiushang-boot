package com.xiushang.framework.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * 分页查询类
 */
public  class BaseSearchPageVo implements Serializable {

    /**
     * 当前的页数
     */
    @ApiModelProperty(notes = "当前的页数")
    protected int pageNo = 1;

    /**
     * 当前每页所显示的行数。
     */
    @ApiModelProperty(notes = "当前每页所显示的行数")
    protected int pageSize = 15;


    public int getPageNo() {
        if(pageNo<=0){
            return 1;
        }
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        if(pageSize<=0){
            return 15;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    @ApiModelProperty(hidden = true)
    public int getOffset() {
        int pageNo = getPageNo() - 1;
        if (pageNo < 0) {
            pageNo = 0;
        }
        return pageNo * getPageSize();
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
