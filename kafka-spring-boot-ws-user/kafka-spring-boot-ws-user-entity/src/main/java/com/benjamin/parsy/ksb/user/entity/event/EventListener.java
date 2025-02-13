package com.benjamin.parsy.ksb.user.entity.event;

public interface EventListener<T> {

    void handleOrderCreatedEvent(T event);

}
