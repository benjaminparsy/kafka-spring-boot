package com.benjamin.parsy.ksb.order.infrastructure.kafka.event.orderconfirmedevent;

import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.OrderKafkaEvent;

import java.util.UUID;

public class OrderConfirmedKafkaEvent extends OrderKafkaEvent {

    protected OrderConfirmedKafkaEvent(UUID orderUuid) {
        super(orderUuid);
    }

}
