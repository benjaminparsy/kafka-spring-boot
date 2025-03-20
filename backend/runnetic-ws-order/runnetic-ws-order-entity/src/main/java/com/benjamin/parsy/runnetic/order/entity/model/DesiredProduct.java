package com.benjamin.parsy.runnetic.order.entity.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode
@ToString
@Getter
public class DesiredProduct {

    private final UUID productUuid;
    private final int quantity;
    private final double price;

    public DesiredProduct(UUID productUuid, int quantity, double price) {
        if (productUuid == null) {
            throw new IllegalArgumentException("Product UUID cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        this.productUuid = productUuid;
        this.quantity = quantity;
        this.price = price;
    }

    public double totalPriceForProduct() {
        return quantity * price;
    }

}
