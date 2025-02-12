package com.benjamin.parsy.ksb.order.entity.event;

import com.benjamin.parsy.ksb.order.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderPaidEvent;

public interface EventListener {

    void handleCancelOrder(OrderFailedEvent event);

    void handleOrderPaid(OrderPaidEvent event);

}
