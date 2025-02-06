package com.benjamin.parsy.ksb.order.domain.model.event;

import com.benjamin.parsy.ksb.order.domain.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.domain.model.Order;
import com.benjamin.parsy.ksb.order.domain.model.OrderStatus;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class OrderCreatedEvent {

    private final UUID orderUuid;
    private final UUID userUuid;
    private final OffsetDateTime orderDate;
    private final OrderStatus status;
    private final List<DesiredProduct> products;
    private final double totalPrice;

    public OrderCreatedEvent(Order order) {
        this.orderUuid = order.getUuid();
        this.userUuid = order.getUserUuid();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.products = order.getProducts();
        this.totalPrice = order.getTotalPrice();
    }

}
