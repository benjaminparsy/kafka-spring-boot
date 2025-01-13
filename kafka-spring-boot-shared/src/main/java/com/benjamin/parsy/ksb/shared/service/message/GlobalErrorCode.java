package com.benjamin.parsy.ksb.shared.service.message;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCodeOperation {

    UNKNOWN_ERROR_OCCURRED("error.global.1"),
    INPUT_DATA_VALIDATION_ERROR("error.global.2"),
    ;

    private final String baseKey;

    @Override
    public String getCodeKey() {
        return baseKey + ".code";
    }

    @Override
    public String getDescriptionKey() {
        return baseKey + ".desc";
    }

}
