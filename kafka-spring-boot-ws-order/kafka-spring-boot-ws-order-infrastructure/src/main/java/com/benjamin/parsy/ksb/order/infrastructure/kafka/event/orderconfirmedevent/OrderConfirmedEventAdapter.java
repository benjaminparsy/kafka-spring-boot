package com.benjamin.parsy.ksb.order.infrastructure.kafka.event.orderconfirmedevent;

import com.benjamin.parsy.ksb.order.domain.model.event.OrderConfirmedEvent;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.EventKafkaAdapter;
import org.springframework.stereotype.Component;

@Component
public class OrderConfirmedEventAdapter implements EventKafkaAdapter<OrderConfirmedEvent, OrderConfirmedKafkaEvent> {

    @Override
    public OrderConfirmedKafkaEvent toKafkaEvent(OrderConfirmedEvent modelEvent) {
        return new OrderConfirmedKafkaEvent(modelEvent.uuid());
    }

}
