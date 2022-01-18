package com.xiushang.framework.entity.vo;

import io.swagger.annotations.ApiModelProperty;

public abstract class BaseVO implements java.io.Serializable {

    @ApiModelProperty(notes = "主键ID")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
