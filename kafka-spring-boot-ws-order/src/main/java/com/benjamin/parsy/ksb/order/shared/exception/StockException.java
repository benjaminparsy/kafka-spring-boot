package com.benjamin.parsy.ksb.order.shared.exception;

import com.benjamin.parsy.ksb.shared.exception.BusinessException;
import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;

public class StockException extends BusinessException {

    public StockException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
