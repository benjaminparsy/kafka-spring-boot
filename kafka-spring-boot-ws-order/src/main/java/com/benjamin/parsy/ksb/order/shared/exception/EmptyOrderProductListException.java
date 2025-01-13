package com.benjamin.parsy.ksb.order.shared.exception;

import com.benjamin.parsy.ksb.shared.exception.AbstractMessageException;
import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;

public class EmptyOrderProductListException extends AbstractMessageException {

    public EmptyOrderProductListException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
