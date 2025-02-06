package com.benjamin.parsy.ksb.order.application.rest.message;

import org.springframework.lang.NonNull;

import java.util.Locale;

public interface MessageService {

    @NonNull
    ErrorMessage getErrorMessage(String code, Locale locale, Object... args);

}
