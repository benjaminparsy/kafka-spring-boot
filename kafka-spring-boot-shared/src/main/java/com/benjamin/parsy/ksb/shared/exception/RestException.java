package com.benjamin.parsy.ksb.shared.exception;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public RestException(ErrorMessage errorMessage) {
        super(errorMessage.getFormattedMessage());
        this.errorMessage = errorMessage;
    }

    public String getCode() {
        return errorMessage.getCode();
    }

    public String getDescription() {
        return errorMessage.getDescription();
    }

}
