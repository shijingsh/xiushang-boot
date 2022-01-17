package com.xiushang.security.authentication.social;

import com.xiushang.common.user.vo.SocialLoginVo;
import com.xiushang.security.authentication.BaseAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class SocialAuthenticationToken extends BaseAuthenticationToken {

    private static final long serialVersionUID = 420L;

    /**
     * 社交账号ID
     */
    private final Object principal;

    /**
     * 社交账号
     */
    private SocialLoginVo socialLoginVo;

    public SocialAuthenticationToken(SocialLoginVo socialLoginVo) {
        super(null,socialLoginVo.getClientId());
        this.principal = socialLoginVo.getSocialId();
        this.socialLoginVo = socialLoginVo;
        setAuthenticated(false);
    }

    public SocialAuthenticationToken(String clientId, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities,clientId);
        this.principal =principal;
        super.setAuthenticated(true);
    }

    public SocialLoginVo getSocialLoginVo() {
        return socialLoginVo;
    }

    public void setSocialLoginVo(SocialLoginVo socialLoginVo) {
        this.socialLoginVo = socialLoginVo;
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

}
