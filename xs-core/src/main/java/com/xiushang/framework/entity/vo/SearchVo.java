package com.xiushang.framework.entity.vo;

import io.swagger.annotations.ApiModelProperty;

public class SearchVo extends SearchPageVo {

    @ApiModelProperty(value = "搜索关键字")
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
