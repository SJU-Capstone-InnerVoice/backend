package com.innervoice.user.exception;

import com.innervoice.common.exception.BaseException;
import com.innervoice.common.exception.BaseExceptionType;

public class UserException extends BaseException {

    private final UserExceptionType exceptionType;

    public UserException(UserExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}