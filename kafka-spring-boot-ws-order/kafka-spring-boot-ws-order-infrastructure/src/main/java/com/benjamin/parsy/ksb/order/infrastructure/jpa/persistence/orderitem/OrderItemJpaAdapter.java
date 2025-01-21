package com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.orderitem;

import com.benjamin.parsy.ksb.order.domain.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.order.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemJpaAdapter {

    public DesiredProduct toDomainModel(OrderItemEntity entity) {
        return new DesiredProduct(
                entity.getProductUuid(),
                entity.getQuantity(),
                entity.getPriceAtOrder()
        );
    }

    public List<DesiredProduct> toDomainModel(List<OrderItemEntity> orderItemEntityList) {
        return orderItemEntityList.stream().map(this::toDomainModel).toList();
    }

    public OrderItemEntity toEntity(DesiredProduct desiredProduct, OrderEntity order) {
        return OrderItemEntity.builder()
                .productUuid(desiredProduct.productUuid())
                .quantity(desiredProduct.quantity())
                .priceAtOrder(desiredProduct.price())
                .orderEntity(order)
                .build();
    }

    public List<OrderItemEntity> toEntity(List<DesiredProduct> modelList, OrderEntity order) {
        return modelList.stream().map(dp -> toEntity(dp, order)).toList();
    }

}
