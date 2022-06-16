package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class RegisterVo implements java.io.Serializable {
    @ApiModelProperty(notes = "用户名(手机号码)",required = true)
    private String loginName;
    @ApiModelProperty(notes = "昵称")
    private String name;
    @ApiModelProperty(notes = "密码",required = true)
    private String password;
    @ApiModelProperty(notes = "短信验证码",required = true)
    private String verifyCode;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
