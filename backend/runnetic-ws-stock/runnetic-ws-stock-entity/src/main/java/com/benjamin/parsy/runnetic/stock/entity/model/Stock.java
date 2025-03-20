package com.benjamin.parsy.runnetic.stock.entity.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode
@ToString
@Getter
public class Stock {

    private final UUID uuid;
    private final Product product;
    private int quantity;

    public Stock(UUID uuid, Product product, int quantity) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null.");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.uuid = uuid;
        this.product = product;
        this.quantity = quantity;
    }

    public boolean isEnoughQuantity(int desiredQuantity) {
        return quantity >= desiredQuantity;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
    }

}
