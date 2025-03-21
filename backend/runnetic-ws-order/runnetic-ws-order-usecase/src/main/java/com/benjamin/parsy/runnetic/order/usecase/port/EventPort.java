package com.benjamin.parsy.runnetic.order.usecase.port;

import com.benjamin.parsy.runnetic.order.entity.model.event.Event;

public interface EventPort {

    void publish(Event event);

}
