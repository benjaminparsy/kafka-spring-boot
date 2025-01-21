package com.benjamin.parsy.ksb.order.application.rest.message;

import org.springframework.lang.NonNull;

public interface MessageService {

    @NonNull
    ErrorMessage getErrorMessage(String code, Object... args);

}
