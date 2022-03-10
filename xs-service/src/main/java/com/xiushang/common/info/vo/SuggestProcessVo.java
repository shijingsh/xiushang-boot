package com.xiushang.common.info.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SuggestProcessVo  implements java.io.Serializable {

    @ApiModelProperty(notes = "主键ID",required = true)
    private String id;

    @ApiModelProperty(value = "处理备注")
    private String handlerContent;

}
