package com.xiushang.common.info.vo;

import com.xiushang.framework.entity.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SuggestVo extends BaseVO {

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name ;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式",required = true)
    private String mobile ;
    /**
     * 反馈内容
     */
    @ApiModelProperty(value = "反馈内容",required = true)
    private String content;
    /**
     * email
     */
    @ApiModelProperty(value = "email")
    private String email;

    /**
     * 相关图片
     */
    @ApiModelProperty(value = "相关图片")
    private List<String> images = new ArrayList<>();


}
