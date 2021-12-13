package com.xiushang.security.authentication.mobile;

import com.xiushang.security.authentication.BaseAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 手机验证码登录
 *
 */
public class SmsCodeAuthenticationToken extends BaseAuthenticationToken {

    private static final long serialVersionUID = 550L;
    private final Object principal;
    private Object credentials;

    public SmsCodeAuthenticationToken(String clientId,Object principal, Object credentials) {
        super((Collection) null,clientId);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public SmsCodeAuthenticationToken(String clientId,Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities,clientId);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

}
