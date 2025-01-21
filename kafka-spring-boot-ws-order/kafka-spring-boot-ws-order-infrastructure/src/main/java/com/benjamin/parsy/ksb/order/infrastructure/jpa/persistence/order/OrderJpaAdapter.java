package com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.order;

import com.benjamin.parsy.ksb.order.domain.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.domain.model.Order;
import com.benjamin.parsy.ksb.order.domain.model.OrderStatus;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.orderitem.OrderItemEntity;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.orderitem.OrderItemJpaAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter {

    private final OrderItemJpaAdapter orderItemJpaAdapter;

    public Order toDomainModel(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntityList) {

        OrderStatus orderStatus = OrderStatus.safeValueOf(orderEntity.getOrderStatusEnum().name());

        List<DesiredProduct> desiredProductList = orderItemJpaAdapter.toDomainModel(orderItemEntityList);

        return new Order(orderEntity.getUuid(),
                orderEntity.getUserUuid(), orderEntity.getOrderDate(),
                orderStatus,
                desiredProductList,
                orderEntity.getTotalPrice());
    }

    public OrderEntity toEntity(Order order) {

        OrderStatusEnum orderStatusEnum = OrderStatusEnum.safeValueOf(order.getStatus().name());

        return OrderEntity.builder()
                .uuid(order.getUuid())
                .userUuid(order.getUserUuid())
                .orderDate(order.getOrderDate())
                .orderStatusEnum(orderStatusEnum)
                .totalPrice(order.getTotalPrice())
                .build();

    }

}
