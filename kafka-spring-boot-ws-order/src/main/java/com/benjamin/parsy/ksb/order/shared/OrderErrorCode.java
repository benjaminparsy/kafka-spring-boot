package com.benjamin.parsy.ksb.order.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderErrorCode {

    USERPROJECTION_NOT_FOUND("[EWSO-1]"),
    TECHNICAL_ERROR_OCCURRED("[EWSO-2]"),
    INSUFFICIENT_STOCK("[EWSO-3]"),
    PRODUCTS_NOT_EXISTS("[EWSO-4]"),
    KAFKA_MESSAGE_CANNOT_BE_CREATED("[EWSO-5]"),
    CANNOT_JSONIFY_OBJECT("[EWSO-6]"),
    PRODUCT_NOT_FOUND("[EWSO-7]")
    ;

    private final String code;

}
