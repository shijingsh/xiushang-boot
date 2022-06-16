package com.xiushang.common.user.vo;

import com.xiushang.framework.entity.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Lob;

@Data
public class SystemParamSaveVo extends BaseVO {

    /**
     * 参数中文名称
     */
    @ApiModelProperty(notes = "参数名称",required = true)
    private String paramName;
    /**
     * 参数值
     */
    @Lob
    @ApiModelProperty(notes = "参数值",required = true)
    private String paramValue;

    /**
     * 参数说明
     */
    @ApiModelProperty(notes = "备注")
    private String remark;
}
