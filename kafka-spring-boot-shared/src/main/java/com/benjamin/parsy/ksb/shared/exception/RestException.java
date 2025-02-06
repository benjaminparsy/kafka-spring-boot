package com.benjamin.parsy.ksb.shared.exception;

import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;
import lombok.Getter;

@Getter
public class RestException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public RestException(ErrorMessage errorMessage) {
        super(errorMessage.getFormattedMessage());
        this.errorMessage = errorMessage;
    }

}
