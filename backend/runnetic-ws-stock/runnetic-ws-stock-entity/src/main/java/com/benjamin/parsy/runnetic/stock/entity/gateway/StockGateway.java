package com.benjamin.parsy.runnetic.stock.entity.gateway;

import com.benjamin.parsy.runnetic.stock.entity.exception.StockNotFoundException;
import com.benjamin.parsy.runnetic.stock.entity.model.Stock;

import java.util.UUID;

public interface StockGateway {

    void update(Stock stock);

    Stock findByProductUuid(UUID productUuuid) throws StockNotFoundException;

}
