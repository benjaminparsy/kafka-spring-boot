package com.benjamin.parsy.ksb.shared.exception;

import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;

public interface MessageException {

    String getCode();

    String getDescription();

    ErrorMessage getErrorMessage();

}
