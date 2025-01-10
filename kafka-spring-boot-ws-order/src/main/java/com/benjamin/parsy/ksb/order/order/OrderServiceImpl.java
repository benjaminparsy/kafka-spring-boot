package com.benjamin.parsy.ksb.order.order;

import com.benjamin.parsy.ksb.order.userprojection.UserProjection;
import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class OrderServiceImpl extends GenericServiceImpl<Order> implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository, MessageService messageService) {
        super(repository, messageService);
        this.repository = repository;
    }

    @Override
    public Order createOrder(OffsetDateTime orderDate, String orderStatus, UserProjection userProjection, int totalPrice) {

        if (orderDate == null) {
            orderDate = OffsetDateTime.now();
        }

        OrderStatusEnum orderStatusEnum = OrderStatusEnum.safeValueOf(orderStatus)
                .orElse(OrderStatusEnum.CREATED);

        return repository.save(Order.builder()
                .orderDate(orderDate)
                .orderStatusEnum(orderStatusEnum)
                .orderTotal(totalPrice)
                .userProjection(userProjection)
                .build());
    }
}
