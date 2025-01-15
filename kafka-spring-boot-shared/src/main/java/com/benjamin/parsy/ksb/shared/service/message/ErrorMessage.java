package com.benjamin.parsy.ksb.shared.service.message;

import lombok.Getter;

@Getter
public class ErrorMessage {

    private static final String SPACE = " ";

    private final String code;
    private final String message;
    private final String formattedMessage;

    public ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
        this.formattedMessage = code.concat(SPACE).concat(message);
    }

}
