package com.benjamin.parsy.ksb.shared.service.message;

import org.springframework.lang.NonNull;

public interface MessageService {

    @NonNull
    ErrorMessage getErrorMessage(String code, Object... args);

}
