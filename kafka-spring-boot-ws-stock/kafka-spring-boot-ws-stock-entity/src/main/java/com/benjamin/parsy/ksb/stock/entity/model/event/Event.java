package com.benjamin.parsy.ksb.stock.entity.model.event;

import lombok.Getter;

@Getter
public abstract class Event {

    public final EventType eventType;

    protected Event(EventType eventType) {
        this.eventType = eventType;
    }

}
