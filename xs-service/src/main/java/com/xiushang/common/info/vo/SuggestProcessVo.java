package com.xiushang.common.info.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SuggestProcessVo  implements java.io.Serializable {

    @ApiModelProperty(notes = "主键ID",required = true)
    private String id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name ;

    /**
     * email
     */
    @ApiModelProperty(value = "email")
    private String email;


    @ApiModelProperty(value = "处理备注")
    private String handlerContent;


    @ApiModelProperty(value = "反馈内容")
    private String content;

}
