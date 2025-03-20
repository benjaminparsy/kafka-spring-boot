package com.benjamin.parsy.runnetic.order.entity.gateway;

import com.benjamin.parsy.runnetic.order.entity.model.event.OrderEvent;

public interface EventGateway {

    void publish(OrderEvent orderEvent);

}
