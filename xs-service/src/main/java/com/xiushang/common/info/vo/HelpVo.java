package com.xiushang.common.info.vo;

import com.xiushang.framework.entity.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HelpVo extends BaseVO {

    @ApiModelProperty(value = "标题",required = true)
    private String title;
    @ApiModelProperty(value = "内容",required = true)
    private String content;

    @ApiModelProperty(value = "排序值")
    private Integer displayOrder = 999;
}
