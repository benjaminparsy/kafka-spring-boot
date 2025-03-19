package com.benjamin.parsy.ksb.stock.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderFailedEvent extends Event {

    private final UUID orderUuid;
    private final String cause;

    public OrderFailedEvent(UUID orderUuid, String cause) {
        super(EventType.ORDER_FAILED);
        this.orderUuid = orderUuid;
        this.cause = cause;
    }

}
