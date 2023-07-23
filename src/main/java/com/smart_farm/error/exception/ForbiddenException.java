package com.smart_farm.error.exception;

import com.smart_farm.error.ErrorCode;

public class ForbiddenException extends BusinessException{
    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
