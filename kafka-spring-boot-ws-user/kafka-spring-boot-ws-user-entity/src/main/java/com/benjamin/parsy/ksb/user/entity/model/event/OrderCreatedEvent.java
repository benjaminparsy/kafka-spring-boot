package com.benjamin.parsy.ksb.user.entity.model.event;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class OrderCreatedEvent extends Event {

    private final UUID orderUuid;
    private final UUID userUuid;
    private final String orderDate;
    private final String status;
    private final List<DesiredProduct> products;
    private final double totalPrice;

    protected OrderCreatedEvent(UUID orderUuid, UUID userUuid, String orderDate, String status, List<DesiredProduct> products, double totalPrice) {
        super("ORDER_CREATED");
        this.orderUuid = orderUuid;
        this.userUuid = userUuid;
        this.orderDate = orderDate;
        this.status = status;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public record DesiredProduct(UUID productUuid, int quantity, double price){}

}
