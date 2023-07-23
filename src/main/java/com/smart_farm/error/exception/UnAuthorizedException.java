package com.smart_farm.error.exception;

import com.smart_farm.error.ErrorCode;

public class UnAuthorizedException extends BusinessException{
    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
