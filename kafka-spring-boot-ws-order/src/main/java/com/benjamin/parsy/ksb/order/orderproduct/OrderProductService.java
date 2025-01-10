package com.benjamin.parsy.ksb.order.orderproduct;

import com.benjamin.parsy.ksb.order.order.Order;
import com.benjamin.parsy.ksb.shared.service.GenericService;

import java.util.List;
import java.util.Map;

public interface OrderProductService extends GenericService<OrderProduct> {

    List<OrderProduct> createOrderProductList(Map<Long, Integer> quantityByProductId, Order order);

}
