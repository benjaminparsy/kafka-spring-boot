package com.benjamin.parsy.ksb.order.stockprojection;

import com.benjamin.parsy.ksb.order.shared.exception.StockException;
import com.benjamin.parsy.ksb.shared.service.jpa.GenericService;
import org.springframework.lang.NonNull;

import java.util.Map;

public interface StockProjectionService extends GenericService<StockProjection> {

    /**
     * Calculate the total price of an order
     *
     * @param quantityByProductId Map of desired quantities by desired products
     * @return Total price of order
     * @throws StockException Exception throw if there is an inconsistency between desired and existing products
     */
    int getTotalPrice(@NonNull Map<Long, Integer> quantityByProductId) throws StockException;

    /**
     * Allows you to check whether you have enough stock of the products you want.
     * An exception is raised if there is insufficient stock of a product.
     *
     * @param quantityByProductId Map of desired quantities by desired products
     * @throws StockException Exception throw if there is insufficient stock of a desired product
     */
    void checkQuantity(Map<Long, Integer> quantityByProductId) throws StockException;

}
