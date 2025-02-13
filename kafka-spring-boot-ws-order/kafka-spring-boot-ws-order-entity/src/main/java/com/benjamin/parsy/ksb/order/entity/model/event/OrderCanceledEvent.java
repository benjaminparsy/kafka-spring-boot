package com.benjamin.parsy.ksb.order.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderCanceledEvent extends OrderEvent {

    private final String cause;

    public OrderCanceledEvent(UUID orderUuid, String cause) {
        super(EventType.ORDER_CANCELED, orderUuid);
        this.cause = cause;
    }

}
