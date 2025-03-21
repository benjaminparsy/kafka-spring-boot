package com.benjamin.parsy.runnetic.stock.usecase.port;

import com.benjamin.parsy.runnetic.stock.entity.model.event.Event;

public interface EventPort {

    void publish(Event event);

}
