package com.benjamin.parsy.ksb.order.order;

import com.benjamin.parsy.ksb.order.shared.exception.StockException;
import com.benjamin.parsy.ksb.shared.service.jpa.GenericService;

import java.time.OffsetDateTime;
import java.util.Map;

public interface OrderService extends GenericService<Order> {

    /**
     * @param orderDate           Order date
     * @param orderStatus         Order status
     * @param userProjectionId    User projection ID to link to the Order
     * @param quantityByProductId Desired products with desired quantity
     * @return Order recorded in the database
     */
    Order createOrder(OffsetDateTime orderDate, String orderStatus, Long userProjectionId,
                      Map<Long, Integer> quantityByProductId) throws StockException;

}
