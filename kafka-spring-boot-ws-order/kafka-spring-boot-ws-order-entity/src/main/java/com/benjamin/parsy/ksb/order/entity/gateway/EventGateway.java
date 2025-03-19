package com.benjamin.parsy.ksb.order.entity.gateway;

import com.benjamin.parsy.ksb.order.entity.model.event.OrderEvent;

public interface EventGateway {

    void publish(OrderEvent orderEvent);

}
