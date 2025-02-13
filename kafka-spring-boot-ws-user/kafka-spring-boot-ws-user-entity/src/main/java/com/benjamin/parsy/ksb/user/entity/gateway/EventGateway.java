package com.benjamin.parsy.ksb.user.entity.gateway;

import com.benjamin.parsy.ksb.user.entity.model.event.Event;

public interface EventGateway {

    void save(Event event);

}
