package com.benjamin.parsy.runnetic.stock.usecase;

import com.benjamin.parsy.runnetic.stock.usecase.exception.StockNotFoundException;
import com.benjamin.parsy.runnetic.stock.usecase.port.EventPort;
import com.benjamin.parsy.runnetic.stock.usecase.port.StockPort;
import com.benjamin.parsy.runnetic.stock.entity.model.Stock;
import com.benjamin.parsy.runnetic.stock.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.runnetic.stock.entity.model.event.StockReservedEvent;
import com.benjamin.parsy.runnetic.stock.usecase.publicdata.IDesiredProductPublicData;
import com.benjamin.parsy.runnetic.stock.usecase.exception.InsufficientStockException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ReservedStockUseCase {

    private final StockPort stockPort;
    private final EventPort eventPort;

    public void reservedStock(UUID orderUuid, List<IDesiredProductPublicData> desiredProducts) {

        try {
            reservedStock(desiredProducts);
            publishStockReservedEvent(orderUuid);
        } catch (StockNotFoundException | InsufficientStockException e) {
            handleFailure(orderUuid, e);
        }

    }

    protected void reservedStock(List<IDesiredProductPublicData> desiredProducts) throws StockNotFoundException, InsufficientStockException {

        for (IDesiredProductPublicData desiredProduct : desiredProducts) {

            Stock stock = stockPort.findByProductUuid(desiredProduct.productUuid());
            int desiredQuantity = desiredProduct.quantity();

            if (!stock.isEnoughQuantity(desiredQuantity)) {
                throw new InsufficientStockException(stock.getUuid());
            }

            stock.decreaseQuantity(desiredQuantity);
            stockPort.update(stock);

        }

    }

    private void publishStockReservedEvent(UUID orderUuid) {
        StockReservedEvent event = new StockReservedEvent(orderUuid);
        eventPort.publish(event);
    }

    private void handleFailure(UUID orderUuid, Exception e) {
        OrderFailedEvent event = new OrderFailedEvent(orderUuid, e.getMessage());
        eventPort.publish(event);
    }

}
