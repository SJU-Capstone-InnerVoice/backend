package com.innervoice.conversation.exception;

import com.innervoice.common.exception.BaseException;
import com.innervoice.common.exception.BaseExceptionType;

public class ConversationException extends BaseException {

    private final ConversationExceptionType exceptionType;

    public ConversationException(ConversationExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}