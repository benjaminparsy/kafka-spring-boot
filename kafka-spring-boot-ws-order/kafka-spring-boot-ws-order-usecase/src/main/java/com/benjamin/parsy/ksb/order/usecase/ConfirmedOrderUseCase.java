package com.benjamin.parsy.ksb.order.usecase;

import com.benjamin.parsy.ksb.order.entity.event.EventPublisher;
import com.benjamin.parsy.ksb.order.entity.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.entity.gateway.OrderGateway;
import com.benjamin.parsy.ksb.order.entity.model.Order;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderConfirmedEvent;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ConfirmedOrderUseCase {

    private final OrderGateway orderGateway;
    private final EventPublisher eventPublisher;

    public void confirmOrder(UUID orderUuid) throws OrderNotFoundException {

        Order order = orderGateway.findById(orderUuid);
        order.confirm();
        orderGateway.update(order);

        OrderConfirmedEvent orderConfirmedEvent = new OrderConfirmedEvent(order.getUuid());
        eventPublisher.publishOrderConfirmedEvent(orderConfirmedEvent);

    }

}
