package com.benjamin.parsy.ksb.stock.entity.gateway;

import com.benjamin.parsy.ksb.stock.entity.model.event.Event;

public interface EventGateway {

    void publish(Event event);

}
