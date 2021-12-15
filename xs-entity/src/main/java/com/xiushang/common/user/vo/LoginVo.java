package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class LoginVo extends  BaseLoginVo{

    /**登录名 */
    @ApiModelProperty(value = "登录名")
    private String loginName;

    /**密码 */
    @ApiModelProperty(value = "密码")
    private String password;

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

}
