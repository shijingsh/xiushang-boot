package com.xiushang.security.exception;

import org.springframework.security.core.AuthenticationException;


public class ValidateException extends AuthenticationException {
    public ValidateException(String msg) {
        super(msg);
    }
}
