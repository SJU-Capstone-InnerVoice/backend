package com.innervoice.common.exception;

public class CommonException extends BaseException {

    private final CommonExceptionType commonExceptionType;

    public CommonException(CommonExceptionType commonExceptionType) {
        this.commonExceptionType = commonExceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return commonExceptionType;
    }
}
