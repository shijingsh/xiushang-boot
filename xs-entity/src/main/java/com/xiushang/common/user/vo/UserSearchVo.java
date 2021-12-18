package com.xiushang.common.user.vo;

import com.xiushang.framework.entity.vo.MgPageable;
import io.swagger.annotations.ApiModelProperty;

public class UserSearchVo extends MgPageable implements java.io.Serializable {
    @ApiModelProperty(notes = "用户名称")
    private  String name;
    @ApiModelProperty(notes = "登录名")
    private  String loginName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
