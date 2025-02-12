package com.benjamin.parsy.ksb.user.entity.model.event;

import lombok.Getter;

@Getter
public abstract class Event {

    private final EventType type;

    protected Event(EventType type) {
        this.type = type;
    }

}
