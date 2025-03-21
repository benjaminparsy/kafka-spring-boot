package com.benjamin.parsy.runnetic.order.usecase;

import com.benjamin.parsy.runnetic.order.usecase.port.EventPort;
import com.benjamin.parsy.runnetic.order.usecase.port.OrderPort;
import com.benjamin.parsy.runnetic.order.entity.model.DesiredProduct;
import com.benjamin.parsy.runnetic.order.entity.model.Order;
import com.benjamin.parsy.runnetic.order.entity.model.event.OrderCreatedEvent;
import com.benjamin.parsy.runnetic.order.usecase.publicdata.IDesiredProductPublicData;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderPort orderPort;
    private final EventPort eventPort;

    public Order createOrder(UUID userUuid, List<IDesiredProductPublicData> products) {

        // 1. create and save order
        List<DesiredProduct> desiredProducts = products.stream()
                .map(p -> new DesiredProduct(p.productUuid(), p.quantity(), p.price()))
                .toList();

        Order order = new Order(userUuid, desiredProducts);
        order = orderPort.save(order);

        // 2. Publish an order creation event
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order);
        eventPort.publish(orderCreatedEvent);

        return order;
    }

}
