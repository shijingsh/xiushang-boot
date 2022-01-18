package com.xiushang.framework.entity.vo;

import io.swagger.annotations.ApiModelProperty;

public abstract class BaseVO implements java.io.Serializable {

    @ApiModelProperty(notes = "主键ID(修改时必传)")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
