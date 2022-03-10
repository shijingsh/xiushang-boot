package com.xiushang.common.info.vo;

import com.xiushang.framework.entity.vo.BaseSearchPageVo;
import com.xiushang.framework.entity.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SuggestSearchVo  extends BaseSearchPageVo {

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name ;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String mobile ;

    /**
     * 反馈状态 0 用户反馈  1 处理中  2 已处理
     */
    @ApiModelProperty(value = "反馈状态 0 用户反馈  1 处理中  2 已处理")
    private Integer status;

    /**
     * 反馈类型
     * 0 用户主动反馈
     * 1 用户被动相应 （客户要求给他来电）
     *
     */
    @ApiModelProperty(value = "反馈类型 默认 0")
    private Integer type;


}
