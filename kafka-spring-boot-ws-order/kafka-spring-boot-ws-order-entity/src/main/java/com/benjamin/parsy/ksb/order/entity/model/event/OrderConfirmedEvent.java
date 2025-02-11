package com.benjamin.parsy.ksb.order.entity.model.event;

import java.util.UUID;

public class OrderConfirmedEvent extends OrderEvent {

    public OrderConfirmedEvent(UUID uuid) {
        super("ORDER_CONFIRMED", uuid);
    }

}
