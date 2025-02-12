package com.benjamin.parsy.ksb.order.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderPaidEvent extends OrderEvent {

    public OrderPaidEvent(UUID uuid) {
        super(EventType.ORDER_PAID, uuid);
    }

}
