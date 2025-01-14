package com.benjamin.parsy.ksb.order.orderproduct;

import com.benjamin.parsy.ksb.order.order.Order;
import com.benjamin.parsy.ksb.shared.service.jpa.GenericService;

import java.util.List;
import java.util.Map;

public interface OrderProductService extends GenericService<OrderProduct> {

    /**
     * @param quantityByProductId Map of desired quantities by desired products
     * @param order               Order for which to create OrderProduct
     * @return List of OrderProduct recorded in the database
     */
    List<OrderProduct> createOrderProductList(Map<Long, Integer> quantityByProductId, Order order);

}
