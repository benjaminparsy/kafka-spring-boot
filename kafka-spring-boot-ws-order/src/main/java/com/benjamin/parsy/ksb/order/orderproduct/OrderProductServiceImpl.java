package com.benjamin.parsy.ksb.order.orderproduct;

import com.benjamin.parsy.ksb.order.order.Order;
import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderProductServiceImpl extends GenericServiceImpl<OrderProduct> implements OrderProductService {

    private final OrderProductRepository repository;

    public OrderProductServiceImpl(OrderProductRepository repository, MessageService messageService) {
        super(repository, messageService);
        this.repository = repository;
    }

    @Override
    public List<OrderProduct> createOrderProductList(Map<Long, Integer> quantityByProductId, Order order) {

        List<OrderProduct> orderProductList = quantityByProductId.entrySet().stream()
                .map(entry -> OrderProduct.builder()
                        .quantity(entry.getValue())
                        .order(order)
                        .productId(entry.getKey())
                        .build())
                .toList();

        return repository.saveAll(orderProductList);
    }

}
