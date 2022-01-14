package com.xiushang.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class NewsDTO implements java.io.Serializable{

    @ApiModelProperty(notes = "公告标题",required = true)
    private String title;

    @ApiModelProperty(notes = "公告内容",required = true)
    private String content;

    @ApiModelProperty(notes = "有效期",required = true)
    private Date validDate;
}
