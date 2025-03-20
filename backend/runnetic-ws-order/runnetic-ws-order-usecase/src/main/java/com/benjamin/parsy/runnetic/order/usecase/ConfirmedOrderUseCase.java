package com.benjamin.parsy.runnetic.order.usecase;

import com.benjamin.parsy.runnetic.order.entity.exception.OrderNotFoundException;
import com.benjamin.parsy.runnetic.order.entity.gateway.EventGateway;
import com.benjamin.parsy.runnetic.order.entity.gateway.OrderGateway;
import com.benjamin.parsy.runnetic.order.entity.model.Order;
import com.benjamin.parsy.runnetic.order.entity.model.event.OrderConfirmedEvent;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ConfirmedOrderUseCase {

    private final OrderGateway orderGateway;
    private final EventGateway eventGateway;

    public void confirmOrder(UUID orderUuid) throws OrderNotFoundException {

        Order order = orderGateway.findById(orderUuid);
        order.confirm();
        orderGateway.update(order);

        OrderConfirmedEvent orderConfirmedEvent = new OrderConfirmedEvent(order.getUuid());
        eventGateway.publish(orderConfirmedEvent);

    }

}
