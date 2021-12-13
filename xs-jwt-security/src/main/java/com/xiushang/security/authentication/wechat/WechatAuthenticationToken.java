package com.xiushang.security.authentication.wechat;

import com.xiushang.security.authentication.BaseAuthenticationToken;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;


public class WechatAuthenticationToken extends BaseAuthenticationToken {
    private static final long serialVersionUID = 550L;
    private final Object principal;
    @Getter
    private String encryptedData;
    @Getter
    private String iv;
    /**
     * 账号校验之前的token构建
     *
     * @param principal
     */
    public WechatAuthenticationToken(String clientId,Object principal, String encryptedData,String iv) {
        super(null,clientId);
        this.principal = principal;
        this.encryptedData = encryptedData;
        this.iv=iv;
        setAuthenticated(false);
    }

    /**
     * 账号校验成功之后的token构建
     *
     * @param principal
     * @param authorities
     */
    public WechatAuthenticationToken(String clientId,Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities,clientId);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(isAuthenticated == false, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
