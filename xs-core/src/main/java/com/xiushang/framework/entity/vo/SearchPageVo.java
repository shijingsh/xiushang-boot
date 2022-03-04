package com.xiushang.framework.entity.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页查询类
 */
public  class SearchPageVo extends BaseSearchPageVo {

    @ApiModelProperty(value = "搜索关键字")
    protected String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

}
