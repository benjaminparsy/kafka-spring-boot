package com.benjamin.parsy.runnetic.order.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderConfirmedEvent extends Event {

    private final UUID orderUuid;

    public OrderConfirmedEvent(UUID orderUuid) {
        super(EventType.ORDER_CONFIRMED);
        this.orderUuid = orderUuid;
    }

}
