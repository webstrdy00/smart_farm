package com.smart_farm.error.exception;

import com.smart_farm.error.ErrorCode;

public class InternerServerException extends BusinessException{
    public InternerServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
