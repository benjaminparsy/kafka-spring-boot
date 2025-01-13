package com.benjamin.parsy.ksb.order.shared;

import com.benjamin.parsy.ksb.shared.service.message.ErrorCodeOperation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderErrorCode implements ErrorCodeOperation {

    ITEM_NOT_FOUND_DATABASE("error.ws.order.1"),
    TECHNICAL_ERROR_OCCURRED("error.ws.order.2"),
    INSUFFICIENT_STOCK("error.ws.order.3"),
    PRODUCTS_NOT_EXISTS("error.ws.order.4"),
    KAFKA_MESSAGE_CANNOT_BE_CREATED("error.ws.order.5"),
    CANNOT_JSONIFY_OBJECT("error.ws.order.6"),
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
