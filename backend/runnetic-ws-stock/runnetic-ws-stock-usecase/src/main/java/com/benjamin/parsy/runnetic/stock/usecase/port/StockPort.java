package com.benjamin.parsy.runnetic.stock.usecase.port;

import com.benjamin.parsy.runnetic.stock.usecase.exception.StockNotFoundException;
import com.benjamin.parsy.runnetic.stock.entity.model.Stock;

import java.util.UUID;

public interface StockPort {

    void update(Stock stock);

    Stock findByProductUuid(UUID productUuuid) throws StockNotFoundException;

}
