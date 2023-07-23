package com.smart_farm.error.exception;

import com.smart_farm.error.ErrorCode;

public class NotFoundException extends BusinessException{
    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
