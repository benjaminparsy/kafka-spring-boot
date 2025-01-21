package com.benjamin.parsy.ksb.order.infrastructure.kafka.event.ordercanceledevent;

import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.OrderKafkaEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderCanceledKafkaEvent extends OrderKafkaEvent {

    @JsonProperty("cause")
    private final String cause;

    public OrderCanceledKafkaEvent(UUID orderUuid, String cause) {
        super(orderUuid);
        this.cause = cause;
    }

}
