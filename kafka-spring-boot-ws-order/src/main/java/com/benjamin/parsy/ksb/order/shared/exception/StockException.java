package com.benjamin.parsy.ksb.order.shared.exception;

import com.benjamin.parsy.ksb.shared.exception.AbstractMessageException;
import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;

public class StockException extends AbstractMessageException {

    public StockException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
