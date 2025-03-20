package com.benjamin.parsy.runnetic.stock.entity.gateway;

import com.benjamin.parsy.runnetic.stock.entity.model.event.Event;

public interface EventGateway {

    void publish(Event event);

}
