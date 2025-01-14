package com.benjamin.parsy.ksb.order.stockprojection;

import com.benjamin.parsy.ksb.order.shared.OrderErrorCode;
import com.benjamin.parsy.ksb.order.shared.exception.StockException;
import com.benjamin.parsy.ksb.shared.service.jpa.GenericServiceImpl;
import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;
import com.benjamin.parsy.ksb.shared.service.message.MessageService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockProjectionServiceImpl extends GenericServiceImpl<StockProjection> implements StockProjectionService {

    private final StockProjectionRepository repository;
    private final MessageService messageService;

    public StockProjectionServiceImpl(StockProjectionRepository repository, MessageService messageService) {
        super(repository);
        this.repository = repository;
        this.messageService = messageService;
    }

    @Override
    public int getTotalPrice(@NonNull Map<Long, Integer> quantityByProductId) throws StockException {

        List<StockProjection> stockProjectionList = repository.findAllByProductIdIn(quantityByProductId.keySet());

        chechConsistency(quantityByProductId, stockProjectionList);

        return stockProjectionList.stream()
                .mapToInt(s -> s.getPrice() * quantityByProductId.get(s.getProductId()))
                .sum();
    }

    @Override
    public void checkQuantity(Map<Long, Integer> quantityByProductId) throws StockException {

        List<StockProjection> stockProjectionList = repository.findAllByProductIdIn(quantityByProductId.keySet());

        chechConsistency(quantityByProductId, stockProjectionList);

        for (StockProjection projection : stockProjectionList) {
            validateStockAvailability(quantityByProductId, projection);
        }

    }

    /**
     * Allows you to check the consistency between the products you want and those already available
     *
     * @param quantityByProductId Map of desired quantities by desired products
     * @param stockProjectionList StockProjection list
     * @throws StockException Exception throw if there is an inconsistency between desired and existing products
     */
    private void chechConsistency(Map<Long, Integer> quantityByProductId, List<StockProjection> stockProjectionList) throws StockException {

        Set<Long> retrievedProductIds = stockProjectionList.stream()
                .map(StockProjection::getProductId)
                .collect(Collectors.toSet());

        Set<Long> missingProductIds = quantityByProductId.keySet()
                .stream()
                .filter(productId -> !retrievedProductIds.contains(productId))
                .collect(Collectors.toSet());

        if (!missingProductIds.isEmpty()) {
            ErrorMessage errorMessage = messageService.getErrorMessage(OrderErrorCode.PRODUCTS_NOT_EXISTS, missingProductIds);
            throw new StockException(errorMessage);
        }

    }

    /**
     * Allows you to check that the required quantity of each product is available
     *
     * @param stockProjection StockProjection list
     * @param quantityByProductId Map of desired quantities by desired products
     * @throws StockException Exception throw if there is insufficient stock of a desired product
     */
    private void validateStockAvailability(Map<Long, Integer> quantityByProductId, StockProjection stockProjection) throws StockException {

        int requiredQuantity = quantityByProductId.get(stockProjection.getProductId());

        if (stockProjection.getStockQuantity() < requiredQuantity) {
            ErrorMessage errorMessage = messageService.getErrorMessage(OrderErrorCode.INSUFFICIENT_STOCK, stockProjection.getProductName());
            throw new StockException(errorMessage);
        }

    }

}
