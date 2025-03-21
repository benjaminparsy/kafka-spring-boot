package com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.port;

import com.benjamin.parsy.runnetic.stock.usecase.exception.StockNotFoundException;
import com.benjamin.parsy.runnetic.stock.usecase.port.StockPort;
import com.benjamin.parsy.runnetic.stock.entity.model.Stock;
import com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.mapper.StockMapper;
import com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.repository.StockRepository;
import com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.schema.StockEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JpaStockPort implements StockPort {

    private final StockRepository stockRepository;

    @Override
    public void update(Stock stock) {
        stockRepository.update(stock.getQuantity(), stock.getUuid());
    }

    @Override
    public Stock findByProductUuid(UUID productUuuid) throws StockNotFoundException {

        Optional<StockEntity> optionalStock = stockRepository.findByProductUuid(productUuuid);

        if (optionalStock.isEmpty()) {
            throw new StockNotFoundException("Stock not found for uuid " + productUuuid);
        }

        return StockMapper.toStock(optionalStock.get());
    }

}
