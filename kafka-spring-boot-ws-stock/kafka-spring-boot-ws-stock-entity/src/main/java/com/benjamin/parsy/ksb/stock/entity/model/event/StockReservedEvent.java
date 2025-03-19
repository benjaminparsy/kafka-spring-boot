package com.benjamin.parsy.ksb.stock.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class StockReservedEvent extends Event {

    private final UUID orderUuid;

    public StockReservedEvent(UUID orderUuid) {
        super(EventType.STOCK_RESERVED);
        this.orderUuid = orderUuid;
    }

}
