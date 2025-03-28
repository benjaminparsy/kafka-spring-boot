package com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.mapper;

import com.benjamin.parsy.runnetic.stock.entity.model.Stock;
import com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.schema.StockEntity;

public class StockMapper {

    private StockMapper() {
        // Private constructor for utility class
    }

    public static Stock toStock(StockEntity stockEntity) {

        return new Stock(stockEntity.getUuid(),
                ProductMapper.toProduct(stockEntity.getProduct()),
                stockEntity.getQuantity()
        );
    }

}
