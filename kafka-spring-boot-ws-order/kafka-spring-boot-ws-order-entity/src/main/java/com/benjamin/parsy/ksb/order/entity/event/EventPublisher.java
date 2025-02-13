package com.benjamin.parsy.ksb.order.entity.event;

import com.benjamin.parsy.ksb.order.entity.model.event.OrderCanceledEvent;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderConfirmedEvent;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderCreatedEvent;

public interface EventPublisher {

    void publishOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent);

    void publishOrderConfirmedEvent(OrderConfirmedEvent orderConfirmedEvent);

    void publishOrderCanceledEvent(OrderCanceledEvent orderCanceledEvent);

}
