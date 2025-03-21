package com.benjamin.parsy.runnetic.user.usecase.port;

import com.benjamin.parsy.runnetic.user.entity.model.event.Event;

public interface EventPort {

    void publish(Event event);

}
