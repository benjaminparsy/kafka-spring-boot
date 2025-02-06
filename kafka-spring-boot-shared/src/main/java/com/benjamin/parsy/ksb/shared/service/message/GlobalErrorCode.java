package com.benjamin.parsy.ksb.shared.service.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GlobalErrorCode {

    UNKNOWN_ERROR_OCCURRED("[EG-1]"),
    INPUT_DATA_VALIDATION_ERROR("[EG-2]"),
    ;

    private final String code;

}
