package com.smart_farm.error.exception;

import com.smart_farm.error.ErrorCode;

public class InvalidTokenException extends BusinessException{
    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
