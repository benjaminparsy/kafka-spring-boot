package com.benjamin.parsy.ksb.user.infrastructure.kafka.event.orderfailedevent;

import com.benjamin.parsy.ksb.user.domain.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.user.infrastructure.kafka.event.EventKafkaAdapter;
import org.springframework.stereotype.Component;

@Component
public class OrderFailedEventAdapter implements EventKafkaAdapter<OrderFailedEvent, OrderFailedKafkaEvent> {

    @Override
    public OrderFailedKafkaEvent toKafkaEvent(OrderFailedEvent modelEvent) {
        return new OrderFailedKafkaEvent(
                modelEvent.orderUuid(),
                modelEvent.cause()
        );
    }

}
