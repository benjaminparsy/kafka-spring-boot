package com.benjamin.parsy.ksb.shared.configuration;

import com.benjamin.parsy.ksb.shared.service.message.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class MessageValidator implements CommandLineRunner {

    private final MessageSource messageSource;

    @Override
    public void run(String... args) {

        List<String> requiredKeys = List.of(
                GlobalErrorCode.UNKNOWN_ERROR_OCCURRED.getCode(),
                GlobalErrorCode.INPUT_DATA_VALIDATION_ERROR.getCode()
        );

        for (String key : requiredKeys) {
            try {
                messageSource.getMessage(key, new String[]{}, Locale.getDefault());
            } catch (NoSuchMessageException e) {
                throw new IllegalStateException("Missing required message key: " + key);
            }
        }

    }

}
