package com.benjamin.parsy.runnetic.order.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderCanceledEvent extends Event {

    private final UUID orderUuid;
    private final String cause;

    public OrderCanceledEvent(UUID orderUuid, String cause) {
        super(EventType.ORDER_CANCELED);
        this.cause = cause;
        this.orderUuid = orderUuid;
    }

}
