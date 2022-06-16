package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class ResetPwdVo  implements java.io.Serializable {

    @ApiModelProperty(notes = "登录名(手机号码)",required = true)
    private String loginName;
    @ApiModelProperty(notes = "密码",required = true)
    private String password;
    @ApiModelProperty(notes = "验证码",required = true)
    private String code;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
