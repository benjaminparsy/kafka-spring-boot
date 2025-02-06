package com.benjamin.parsy.ksb.order.domain.publisher;

import com.benjamin.parsy.ksb.order.domain.model.event.OrderCanceledEvent;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderConfirmedEvent;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderCreatedEvent;

public interface OrderEventPublisher {

    void publishCreatedOrder(OrderCreatedEvent event);

    void publishCanceledOrder(OrderCanceledEvent event);

    void publishConfirmedOrder(OrderConfirmedEvent orderConfirmedEvent);
}
