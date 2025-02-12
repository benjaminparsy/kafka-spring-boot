package com.benjamin.parsy.ksb.order.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class OrderEvent extends Event {

    public final UUID uuid;

    protected OrderEvent(EventType eventType, UUID uuid) {
        super(eventType);
        this.uuid = uuid;
    }

}
