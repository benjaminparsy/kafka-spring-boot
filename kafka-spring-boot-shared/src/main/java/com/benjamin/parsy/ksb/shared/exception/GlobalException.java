package com.benjamin.parsy.ksb.shared.exception;

import lombok.Getter;

@Getter
public class GlobalException extends Exception {

    private final ErrorMessage errorMessage;

    public GlobalException(ErrorMessage errorMessage) {
        super(errorMessage.getFormattedMessage());
        this.errorMessage = errorMessage;
    }

}
