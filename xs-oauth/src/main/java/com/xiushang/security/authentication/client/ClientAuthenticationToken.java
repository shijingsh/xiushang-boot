package com.xiushang.security.authentication.client;

import com.xiushang.security.authentication.BaseAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class ClientAuthenticationToken extends BaseAuthenticationToken {

    private static final long serialVersionUID = 420L;

    /**
     * clientId
     */
    private final Object principal;


    public ClientAuthenticationToken( String clientId) {
        super(null,clientId);
        this.principal = clientId;
        setAuthenticated(false);
    }

    public ClientAuthenticationToken(String clientId,Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities,clientId);
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

}
