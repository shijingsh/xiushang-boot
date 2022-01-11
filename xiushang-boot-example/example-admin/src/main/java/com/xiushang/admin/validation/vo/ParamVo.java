package com.xiushang.admin.validation.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.PaperNo;

@Data
public class ParamVo implements java.io.Serializable {

    @ApiModelProperty(value = "名称",notes = "名称",position = 1,required = true)
    private String name;

    @ApiModelProperty(value = "年龄",message = "年龄不能为空！",position = 1,required = true)
    private Integer age;

    @ApiModelProperty(value = "身份证",position = 1)
    @PaperNo
    private String idCard;
}
