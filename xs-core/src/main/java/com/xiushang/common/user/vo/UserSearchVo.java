package com.xiushang.common.user.vo;

import com.xiushang.framework.entity.vo.BaseSearchPageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserSearchVo extends BaseSearchPageVo implements java.io.Serializable {
    @ApiModelProperty(notes = "用户名称")
    private  String name;
    @ApiModelProperty(notes = "登录名")
    private  String loginName;
    @ApiModelProperty(notes = "手机号")
    private String mobile;

    @ApiModelProperty(notes = "是否已删除 （0 未删除  1 已删除）")
    private Integer deleted ;

}
