package com.xiushang.common.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

public class InternalCodeAuthenticationServiceException extends InternalAuthenticationServiceException {

    private int errorCode = 1;

    public InternalCodeAuthenticationServiceException(String message) {
        super(message);
    }

    public InternalCodeAuthenticationServiceException(int errorCode, String message, Throwable cause) {

        super(message, cause);
        this.errorCode = errorCode;
    }

    public InternalCodeAuthenticationServiceException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
