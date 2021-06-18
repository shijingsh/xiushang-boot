package com.xiushang.framework.model;

public class AuthorizationVo implements java.io.Serializable {

    private String token;
    private String tokenHeader;

    public AuthorizationVo(String token, String tokenHeader) {
        this.token = token;
        this.tokenHeader = tokenHeader;
    }

    public AuthorizationVo() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }
}
