package com.innervoice.friend.exception;

import com.innervoice.common.exception.BaseException;
import com.innervoice.common.exception.BaseExceptionType;

public class FriendException extends BaseException {

    private final FriendExceptionType exceptionType;

    public FriendException(FriendExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}