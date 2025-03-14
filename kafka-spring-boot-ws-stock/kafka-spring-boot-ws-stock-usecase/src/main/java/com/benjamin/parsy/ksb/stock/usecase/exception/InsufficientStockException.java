package com.benjamin.parsy.ksb.stock.usecase.exception;

import java.util.UUID;

public class InsufficientStockException extends Exception {

    public InsufficientStockException(UUID productUuid) {
        super("Insufficient stock for product with id : " + productUuid);
    }

}
