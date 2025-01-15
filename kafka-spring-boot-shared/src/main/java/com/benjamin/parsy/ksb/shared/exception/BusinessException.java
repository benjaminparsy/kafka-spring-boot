package com.benjamin.parsy.ksb.shared.exception;

import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;
import lombok.Getter;

@Getter
public abstract class BusinessException extends Exception {

    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getFormattedMessage());
        this.errorMessage = errorMessage;
    }

}
