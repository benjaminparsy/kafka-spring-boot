package com.benjamin.parsy.ksb.order.usecase;

import com.benjamin.parsy.ksb.order.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.order.entity.gateway.OrderGateway;
import com.benjamin.parsy.ksb.order.entity.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.entity.model.Order;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderCreatedEvent;
import com.benjamin.parsy.ksb.order.usecase.dto.IDesiredProductPublicData;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderGateway orderGateway;
    private final EventGateway eventGateway;

    public Order createOrder(UUID userUuid, List<IDesiredProductPublicData> products) {

        // 1. create and save order
        List<DesiredProduct> desiredProducts = products.stream()
                .map(p -> new DesiredProduct(p.productUuid(), p.quantity(), p.price()))
                .toList();

        Order order = new Order(userUuid, desiredProducts);
        order = orderGateway.save(order);

        // 2. Publish an order creation event
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order);
        eventGateway.publish(orderCreatedEvent);

        return order;
    }

}
