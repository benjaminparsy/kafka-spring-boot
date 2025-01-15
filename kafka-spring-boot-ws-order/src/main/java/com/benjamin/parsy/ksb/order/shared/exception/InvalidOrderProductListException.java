package com.benjamin.parsy.ksb.order.shared.exception;

import com.benjamin.parsy.ksb.shared.exception.BusinessException;
import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;

public class InvalidOrderProductListException extends BusinessException {

    public InvalidOrderProductListException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
