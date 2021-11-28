package com.xiushang.security.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public TokenException(String message) {
        super(message);
    }
}
