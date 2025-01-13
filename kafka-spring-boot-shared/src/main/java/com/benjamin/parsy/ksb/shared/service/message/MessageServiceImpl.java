package com.benjamin.parsy.ksb.shared.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    @NonNull
    @Override
    public ErrorMessage getErrorMessage(ErrorCodeOperation errorCodeOperation, Object[] args) {

        String code = getLocalizedMessage(errorCodeOperation.getCodeKey());
        String description = getLocalizedMessage(errorCodeOperation.getDescriptionKey(), args);

        return new ErrorMessage(code, description);
    }

    private String getLocalizedMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

}
