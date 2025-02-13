package com.benjamin.parsy.ksb.order.entity.gateway;

import com.benjamin.parsy.ksb.order.entity.model.event.Event;

public interface EventGateway {

    void save(Event event);

}
