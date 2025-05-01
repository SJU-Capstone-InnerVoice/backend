package com.innervoice.friend.exception;

import com.innervoice.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum FriendExceptionType implements BaseExceptionType {

    FRIEND_REQUEST_NOT_FOUND(2001, HttpStatus.NOT_FOUND, "친구 요청을 찾을 수 없습니다"),
    FRIEND_NOT_FOUND(2002, HttpStatus.NOT_FOUND, "해당 이름의 친구를 찾을 수 없습니다");


    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    FriendExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
