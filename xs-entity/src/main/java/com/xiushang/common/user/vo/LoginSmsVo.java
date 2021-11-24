package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class LoginSmsVo implements java.io.Serializable{

    /**登录名 */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(notes = "短信验证码",required = true)
    private String verifyCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
