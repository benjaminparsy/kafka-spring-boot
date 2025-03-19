package com.benjamin.parsy.ksb.stock.usecase;

import com.benjamin.parsy.ksb.stock.entity.exception.StockNotFoundException;
import com.benjamin.parsy.ksb.stock.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.stock.entity.gateway.StockGateway;
import com.benjamin.parsy.ksb.stock.entity.model.Stock;
import com.benjamin.parsy.ksb.stock.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.stock.entity.model.event.StockReservedEvent;
import com.benjamin.parsy.ksb.stock.usecase.dto.IDesiredProductPublicData;
import com.benjamin.parsy.ksb.stock.usecase.exception.InsufficientStockException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ReservedStockUseCase {

    private final StockGateway stockGateway;
    private final EventGateway eventGateway;

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

            Stock stock = stockGateway.findByProductUuid(desiredProduct.productUuid());
            int desiredQuantity = desiredProduct.quantity();

            if (!stock.isEnoughQuantity(desiredQuantity)) {
                throw new InsufficientStockException(stock.getUuid());
            }

            stock.decreaseQuantity(desiredQuantity);
            stockGateway.update(stock);

        }

    }

    private void publishStockReservedEvent(UUID orderUuid) {
        StockReservedEvent event = new StockReservedEvent(orderUuid);
        eventGateway.publish(event);
    }

    private void handleFailure(UUID orderUuid, Exception e) {
        OrderFailedEvent event = new OrderFailedEvent(orderUuid, e.getMessage());
        eventGateway.publish(event);
    }

}
