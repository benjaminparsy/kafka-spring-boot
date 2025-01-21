package com.benjamin.parsy.ksb.order.infrastructure.kafka.event.ordercanceledevent;

import com.benjamin.parsy.ksb.order.domain.model.event.OrderCanceledEvent;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.EventKafkaAdapter;
import org.springframework.stereotype.Component;

@Component
public class OrderCanceledEventAdapter implements EventKafkaAdapter<OrderCanceledEvent, OrderCanceledKafkaEvent> {

    @Override
    public OrderCanceledKafkaEvent toKafkaEvent(OrderCanceledEvent modelEvent) {
        return new OrderCanceledKafkaEvent(modelEvent.uuid(), modelEvent.cause());
    }

}
