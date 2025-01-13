package com.benjamin.parsy.ksb.shared.exception;

import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;

public class RestException extends RuntimeException implements MessageException {

    private final ErrorMessage errorMessage;

    public RestException(ErrorMessage errorMessage) {
        super(errorMessage.getFormattedMessage());
        this.errorMessage = errorMessage;
    }

    @Override
    public String getCode() {
        return errorMessage.getCode();
    }

    @Override
    public String getDescription() {
        return errorMessage.getDescription();
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
