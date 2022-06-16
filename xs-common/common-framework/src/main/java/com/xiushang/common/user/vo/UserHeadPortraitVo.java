package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class UserHeadPortraitVo implements java.io.Serializable {

    /**
     * 头像
     */
    @ApiModelProperty(notes = "头像",required = true)
    private String headPortrait;


    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }
}
