package com.benjamin.parsy.ksb.order.usecase;

import com.benjamin.parsy.ksb.order.entity.event.EventPublisher;
import com.benjamin.parsy.ksb.order.entity.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.order.entity.gateway.OrderGateway;
import com.benjamin.parsy.ksb.order.entity.model.Order;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderCanceledEvent;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CancelOrderUseCase {

    private final OrderGateway orderGateway;
    private final EventPublisher eventPublisher;
    private final EventGateway eventGateway;

    public void cancelOrder(UUID orderUuid, String cause) throws OrderNotFoundException {

        Order order = orderGateway.findById(orderUuid);
        order.cancel();
        orderGateway.update(order);

        OrderCanceledEvent orderCanceledEvent = new OrderCanceledEvent(order.getUuid(), cause);
        eventGateway.save(orderCanceledEvent);
        eventPublisher.publishOrderCanceledEvent(orderCanceledEvent);

    }

}
