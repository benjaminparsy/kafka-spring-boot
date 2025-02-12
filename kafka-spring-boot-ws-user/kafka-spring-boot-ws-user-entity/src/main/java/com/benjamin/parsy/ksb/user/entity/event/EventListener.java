package com.benjamin.parsy.ksb.user.entity.event;

import com.benjamin.parsy.ksb.user.entity.model.event.OrderCreatedEvent;

public interface EventListener {

    void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent);

}
