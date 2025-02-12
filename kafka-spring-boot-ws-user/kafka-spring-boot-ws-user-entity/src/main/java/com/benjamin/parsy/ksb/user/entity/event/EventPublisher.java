package com.benjamin.parsy.ksb.user.entity.event;

import com.benjamin.parsy.ksb.user.entity.model.event.Event;

public interface EventPublisher {

    void publish(Event event);

}
