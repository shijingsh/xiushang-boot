package com.xiushang.security.authentication.mobile;

import org.springframework.security.core.AuthenticationException;


public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
