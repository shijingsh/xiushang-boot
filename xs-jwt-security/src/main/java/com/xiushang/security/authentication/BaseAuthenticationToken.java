package com.xiushang.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public abstract class BaseAuthenticationToken extends AbstractAuthenticationToken {

    private String clientId;

    public BaseAuthenticationToken(Collection<? extends GrantedAuthority> authorities,String clientId) {
        super(authorities);
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
