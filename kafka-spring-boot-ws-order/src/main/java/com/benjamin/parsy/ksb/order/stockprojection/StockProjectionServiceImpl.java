package com.benjamin.parsy.ksb.order.stockprojection;

import com.benjamin.parsy.ksb.order.exception.StockException;
import com.benjamin.parsy.ksb.shared.exception.ErrorCode;
import com.benjamin.parsy.ksb.shared.exception.ErrorMessage;
import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockProjectionServiceImpl extends GenericServiceImpl<StockProjection> implements StockProjectionService {

    private final StockProjectionRepository repository;
    private final MessageService messageService;

    public StockProjectionServiceImpl(StockProjectionRepository repository,
                                      MessageService messageService,
                                      MessageService messageService1) {
        super(repository, messageService);
        this.repository = repository;
        this.messageService = messageService1;
    }

    @Override
    public void checkQuantity(@NonNull Map<Long, Integer> quantityByProductId) throws StockException {

        List<StockProjection> stockProjectionList = repository.findAllByProductIdIn(quantityByProductId.keySet());

        if (stockProjectionList.isEmpty()) {
            ErrorMessage errorMessage = messageService.getErrorMessage(ErrorCode.NO_PRODUCTS);
            throw new StockException(errorMessage);
        }

        for (StockProjection stockProjection : stockProjectionList) {

            int quantityRequired = quantityByProductId.get(stockProjection.getProductId());

            if (stockProjection.getStockQuantity() < quantityRequired) {
                ErrorMessage errorMessage = messageService.getErrorMessage(ErrorCode.INSUFFICIENT_STOCK, stockProjection.getProductName());
                throw new StockException(errorMessage);
            }

        }

    }

    @Override
    public int getTotalPrice(@NonNull Map<Long, Integer> quantityByProductId) {

        List<StockProjection> stockProjectionList = repository.findAllByProductIdIn(quantityByProductId.keySet());

        int totalPrice = 0;
        for (StockProjection stockProjection : stockProjectionList) {

            int quantity = quantityByProductId.get(stockProjection.getProductId());
            totalPrice += stockProjection.getPrice() * quantity;

        }

        return totalPrice;
    }

}
