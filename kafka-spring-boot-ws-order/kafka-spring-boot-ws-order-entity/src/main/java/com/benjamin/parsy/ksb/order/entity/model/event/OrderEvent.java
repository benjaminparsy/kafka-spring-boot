package com.benjamin.parsy.ksb.order.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class OrderEvent extends Event {

    public final UUID orderUuid;

    protected OrderEvent(EventType eventType, UUID orderUuid) {
        super(eventType);
        this.orderUuid = orderUuid;
    }

}
