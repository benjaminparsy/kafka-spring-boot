package com.benjamin.parsy.ksb.shared.service;

import com.benjamin.parsy.ksb.shared.exception.ErrorCode;
import com.benjamin.parsy.ksb.shared.exception.ErrorMessage;
import org.springframework.lang.NonNull;

public interface MessageService {

    @NonNull
    ErrorMessage getErrorMessage(ErrorCode errorCode, Object... args);

}
