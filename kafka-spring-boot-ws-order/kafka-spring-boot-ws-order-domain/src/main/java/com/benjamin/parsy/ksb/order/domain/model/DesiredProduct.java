package com.benjamin.parsy.ksb.order.domain.model;

import java.util.UUID;

public record DesiredProduct(UUID productUuid, int quantity, double price) {

    public DesiredProduct {
        if (productUuid == null) {
            throw new IllegalArgumentException("Product UUID cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
    }

}
