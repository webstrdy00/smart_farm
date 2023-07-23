package com.smart_farm.error.exception;

import com.smart_farm.error.ErrorCode;

public class BadRequestException extends BusinessException{


    public BadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
