package com.benjamin.parsy.ksb.order.entity.model.event;

import lombok.Getter;

@Getter
public abstract class Event {

    private final String type;

    protected Event(String type) {
        this.type = type;
    }

}
