package com.benjamin.parsy.runnetic.order.infrastructure.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    UNKNOWN_ERROR_OCCURRED("[EG-1]"),
    INPUT_DATA_VALIDATION_ERROR("[EG-2]"),
    ;

    private final String code;

}
