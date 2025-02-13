package com.benjamin.parsy.ksb.order.entity.event;

public interface EventListener<T> {

    void handleCancelOrder(T event);

    void handleOrderPaid(T event);

}
