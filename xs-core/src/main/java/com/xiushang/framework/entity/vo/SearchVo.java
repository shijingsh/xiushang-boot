package com.xiushang.framework.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchVo implements Serializable {

    @ApiModelProperty(value = "搜索关键字")
    protected String searchKey;


}
