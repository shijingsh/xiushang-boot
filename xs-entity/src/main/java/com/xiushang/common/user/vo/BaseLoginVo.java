package com.xiushang.common.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class BaseLoginVo implements java.io.Serializable{
    @ApiModelProperty(value = "客户端ID")
    private String clientId;

    @ApiModelProperty(value = "客户端秘钥")
    private String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
