package com.smart_farm.jwt;

import org.springframework.security.core.AuthenticationException;

public class jwtExpiredException extends AuthenticationException {
    public jwtExpiredException(String message) {
        super(message);
    }
}
