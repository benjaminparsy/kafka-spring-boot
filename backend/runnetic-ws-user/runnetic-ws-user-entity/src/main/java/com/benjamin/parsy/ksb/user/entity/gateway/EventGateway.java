package com.benjamin.parsy.runnetic.user.entity.gateway;

import com.benjamin.parsy.runnetic.user.entity.model.event.Event;

public interface EventGateway {

    void publish(Event event);

}
