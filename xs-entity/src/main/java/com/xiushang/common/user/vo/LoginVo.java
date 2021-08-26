package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class LoginVo implements java.io.Serializable{

    /**登录名 */
    @ApiModelProperty(value = "登录名")
    private String loginName;

    /**密码 */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 最后登录平台
     */
    @ApiModelProperty(value = "登录平台")
    private String lastLoginPlatform;
    /**
     * 推送 clientId
     */
    @ApiModelProperty(value = "推送服务 客户ID")
    private String clientId;

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

    public String getLastLoginPlatform() {
        return lastLoginPlatform;
    }

    public void setLastLoginPlatform(String lastLoginPlatform) {
        this.lastLoginPlatform = lastLoginPlatform;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
