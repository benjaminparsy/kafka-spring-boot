package com.benjamin.parsy.ksb.order.entity.event;

import com.benjamin.parsy.ksb.order.entity.model.event.Event;

public interface EventPublisher {

    void publish(Event event);

}
