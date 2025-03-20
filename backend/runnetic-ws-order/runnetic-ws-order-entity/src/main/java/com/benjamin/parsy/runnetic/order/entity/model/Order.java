package com.benjamin.parsy.runnetic.order.entity.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode
@ToString
@Getter
public class Order {

    private final UUID uuid;
    private final UUID userUuid;
    private final OffsetDateTime orderDate;
    private OrderStatus status;
    private final List<DesiredProduct> products;
    private final double totalPrice;

    public Order(UUID userUuid, List<DesiredProduct> products) {
        if (userUuid == null) {
            throw new IllegalArgumentException("User UUID cannot be null.");
        }
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product.");
        }
        this.uuid = UUID.randomUUID();
        this.userUuid = userUuid;
        this.orderDate = OffsetDateTime.now();
        this.status = OrderStatus.PENDING;
        this.products = products;
        this.totalPrice = calculateTotalPrice();
    }

    public Order(UUID uuid, UUID userUuid, OffsetDateTime orderDate, String status, List<DesiredProduct> products) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.orderDate = orderDate;
        this.status = OrderStatus.safeValueOf(status);
        this.products = products;
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        return products.stream().mapToDouble(DesiredProduct::totalPriceForProduct).sum();
    }

    public void cancel() {
        this.status = OrderStatus.CANCELED;
    }

    public void confirm() {
        this.status = OrderStatus.CONFIRMED;
    }

}
