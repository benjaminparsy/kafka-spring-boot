package com.benjamin.parsy.ksb.order.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class OrderEvent extends Event {

    public final UUID uuid;

    protected OrderEvent(String orderEventType, UUID uuid) {
        super(orderEventType);
        this.uuid = uuid;
    }

}
