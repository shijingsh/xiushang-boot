package com.xiushang.admin.validation.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.IdCard;

@Data
public class ParamVo implements java.io.Serializable {

    @ApiModelProperty(value = "名称name",notes = "名称notes",position = 1,required = true)
    private String name;

    @ApiModelProperty(value = "年龄"/*,message = "年龄不能为空！"*/,position = 2,required = true)
    private Integer age;

    /**
     *  子对象需要添加@Valid 才会校验
     */
    @Valid
    @ApiModelProperty(value = "对象",position = 3)
    private IdCardDTO idCardDTO;
    @Data
    static class IdCardDTO{

        @ApiModelProperty(value = "身份证",position = 1,required = true)
        @IdCard
        private String idCard;

    }
}
