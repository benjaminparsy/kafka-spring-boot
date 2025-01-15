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
    public ErrorMessage getErrorMessage(String key, Object[] args) {
        String message = messageSource.getMessage(key, args, Locale.getDefault());
        return new ErrorMessage(key, message);
    }

}
