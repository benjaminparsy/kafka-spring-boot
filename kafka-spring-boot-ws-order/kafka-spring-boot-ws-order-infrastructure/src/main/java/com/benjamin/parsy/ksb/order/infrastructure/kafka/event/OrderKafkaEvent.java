package com.benjamin.parsy.ksb.order.infrastructure.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class OrderKafkaEvent {

    @JsonProperty("orderUuid")
    private final UUID orderUuid;

    protected OrderKafkaEvent(UUID orderUuid) {
        this.orderUuid = orderUuid;
    }

}
