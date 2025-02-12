package com.benjamin.parsy.ksb.order.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderFailedEvent extends OrderEvent {

    private final String cause;

    public OrderFailedEvent(UUID uuid, String cause) {
        super(EventType.ORDER_FAILED, uuid);
        this.cause = cause;
    }

}
