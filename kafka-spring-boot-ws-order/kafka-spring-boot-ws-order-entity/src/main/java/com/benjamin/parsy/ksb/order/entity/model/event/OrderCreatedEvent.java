package com.benjamin.parsy.ksb.order.entity.model.event;

import com.benjamin.parsy.ksb.order.entity.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.entity.model.Order;
import com.benjamin.parsy.ksb.order.entity.model.OrderStatus;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class OrderCreatedEvent extends OrderEvent {

    private final UUID userUuid;
    private final OffsetDateTime orderDate;
    private final OrderStatus status;
    private final List<DesiredProduct> products;
    private final double totalPrice;

    public OrderCreatedEvent(Order order) {
        super(EventType.ORDER_CREATED, order.getUuid());
        this.userUuid = order.getUserUuid();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.products = order.getProducts();
        this.totalPrice = order.getTotalPrice();
    }

}
