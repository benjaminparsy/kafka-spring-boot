package com.benjamin.parsy.ksb.order.shared.exception;

import com.benjamin.parsy.ksb.shared.exception.BusinessException;
import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;

public class UserProjectionNotFoundException extends BusinessException {

    public UserProjectionNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
