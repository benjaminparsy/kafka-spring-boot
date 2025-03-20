package com.benjamin.parsy.runnetic.stock.entity.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode
@ToString
@Getter
public class Product {

    private final UUID uuid;
    private final String name;
    private final double price;

    public Product(UUID uuid, String name, double price) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        this.uuid = uuid;
        this.name = name;
        this.price = price;
    }

}
