package com.xiushang.framework.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchVo extends SearchPageVo {

    @ApiModelProperty(value = "搜索关键字")
    private String searchKey;


}
