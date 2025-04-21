package com.innervoice.user.exception;

import com.innervoice.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum UserExceptionType implements BaseExceptionType {

    USER_NOT_FOUND(1000, HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다"),
    ALREADY_EXIST_NAME(1001, HttpStatus.CONFLICT, "이미 존재하는 이름입니다");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    UserExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int errorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }
}
