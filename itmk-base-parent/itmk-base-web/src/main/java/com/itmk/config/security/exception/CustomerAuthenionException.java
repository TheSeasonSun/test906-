package com.itmk.config.security.exception;


import org.springframework.security.core.AuthenticationException;

public class CustomerAuthenionException extends AuthenticationException {

    public CustomerAuthenionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CustomerAuthenionException(String msg) {
        super(msg);
    }
}
