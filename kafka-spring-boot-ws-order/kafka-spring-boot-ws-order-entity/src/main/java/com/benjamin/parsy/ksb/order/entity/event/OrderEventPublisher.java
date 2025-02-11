package com.benjamin.parsy.ksb.order.entity.event;

import com.benjamin.parsy.ksb.order.entity.model.event.OrderEvent;

public interface OrderEventPublisher {

    void publish(OrderEvent event);

}
