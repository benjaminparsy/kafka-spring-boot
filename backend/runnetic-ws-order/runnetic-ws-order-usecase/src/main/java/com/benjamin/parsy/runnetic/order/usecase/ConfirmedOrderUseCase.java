package com.benjamin.parsy.runnetic.order.usecase;

import com.benjamin.parsy.runnetic.order.usecase.exception.OrderNotFoundException;
import com.benjamin.parsy.runnetic.order.usecase.port.EventPort;
import com.benjamin.parsy.runnetic.order.usecase.port.OrderPort;
import com.benjamin.parsy.runnetic.order.entity.model.Order;
import com.benjamin.parsy.runnetic.order.entity.model.event.OrderConfirmedEvent;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ConfirmedOrderUseCase {

    private final OrderPort orderPort;
    private final EventPort eventPort;

    public void confirmOrder(UUID orderUuid) throws OrderNotFoundException {

        Order order = orderPort.findById(orderUuid);
        order.confirm();
        orderPort.update(order);

        OrderConfirmedEvent orderConfirmedEvent = new OrderConfirmedEvent(order.getUuid());
        eventPort.publish(orderConfirmedEvent);

    }

}
