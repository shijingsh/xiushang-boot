package com.xiushang.security.authentication.social;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class SocialAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 420L;

    /**
     * 社交账号ID
     */
    private final Object principal;

    /**
     * 社交账号类型
     */
    private String socialType;


    public SocialAuthenticationToken( String socialType,String socialId) {
        super(null);
        this.principal =socialId;
        this.socialType = socialType;
        setAuthenticated(false);
    }

    public SocialAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal =principal;
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

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }
}
