package com.benjamin.parsy.ksb.order.exception;

import com.benjamin.parsy.ksb.shared.exception.ErrorMessage;
import lombok.Getter;

@Getter
public class StockException extends Exception {

    private final ErrorMessage errorMessage;

    public StockException(ErrorMessage errorMessage) {
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
