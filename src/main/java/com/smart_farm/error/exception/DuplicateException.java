package com.smart_farm.error.exception;

import com.smart_farm.error.ErrorCode;

public class DuplicateException extends BusinessException{
    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
