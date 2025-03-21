package com.benjamin.parsy.runnetic.order.usecase;

import com.benjamin.parsy.runnetic.order.usecase.exception.OrderNotFoundException;
import com.benjamin.parsy.runnetic.order.usecase.port.EventPort;
import com.benjamin.parsy.runnetic.order.usecase.port.OrderPort;
import com.benjamin.parsy.runnetic.order.entity.model.Order;
import com.benjamin.parsy.runnetic.order.entity.model.event.OrderCanceledEvent;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CancelOrderUseCase {

    private final OrderPort orderPort;
    private final EventPort eventPort;

    public void cancelOrder(UUID orderUuid, String cause) throws OrderNotFoundException {

        Order order = orderPort.findById(orderUuid);
        order.cancel();
        orderPort.update(order);

        OrderCanceledEvent orderCanceledEvent = new OrderCanceledEvent(order.getUuid(), cause);
        eventPort.publish(orderCanceledEvent);

    }

}
