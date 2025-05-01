package com.innervoice.conversation.exception;

import com.innervoice.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum ConversationExceptionType implements BaseExceptionType {

    CONVERSATION_REQUEST_NOT_FOUND(3001, HttpStatus.NOT_FOUND, "대화 요청을 찾을 수 없습니다");


    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    ConversationExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
