package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemParamVo implements java.io.Serializable{

    /**
     * 参数中文名称
     */
    @ApiModelProperty(notes = "参数名称",required = true)
    private String paramName;
    /**
     * 参数默认值
     */
    @ApiModelProperty(notes = "参数默认值")
    private String defaultValue;

    /**
     * 参数说明
     */
    @ApiModelProperty(notes = "备注")
    private String remark;
}
