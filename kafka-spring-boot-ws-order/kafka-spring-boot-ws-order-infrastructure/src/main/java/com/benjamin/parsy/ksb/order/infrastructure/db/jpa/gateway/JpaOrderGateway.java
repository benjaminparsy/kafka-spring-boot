package com.benjamin.parsy.ksb.order.infrastructure.db.jpa.gateway;

import com.benjamin.parsy.ksb.order.entity.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.entity.gateway.OrderGateway;
import com.benjamin.parsy.ksb.order.entity.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.entity.model.Order;
import com.benjamin.parsy.ksb.order.infrastructure.db.jpa.repository.OrderItemRepository;
import com.benjamin.parsy.ksb.order.infrastructure.db.jpa.repository.OrderRepository;
import com.benjamin.parsy.ksb.order.infrastructure.db.jpa.schema.OrderEntity;
import com.benjamin.parsy.ksb.order.infrastructure.db.jpa.schema.OrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JpaOrderGateway implements OrderGateway {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public Order save(Order order) {

        OrderEntity orderEntity = new OrderEntity(order);
        orderRepository.save(orderEntity);

        List<OrderItemEntity> orderItemEntityList = order.getProducts().stream()
                .map(dp -> new OrderItemEntity(dp, orderEntity))
                .toList();
        orderItemRepository.saveAll(orderItemEntityList);

        return order;
    }

    @Transactional
    @Override
    public void update(Order order) {
        orderRepository.updateByUuid(order.getStatus().name(), order.getTotalPrice(), order.getUuid());
    }

    @Override
    public Order findById(UUID uuid) throws OrderNotFoundException {

        OrderEntity orderEntity = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for id " + uuid));

        List<DesiredProduct> products = orderItemRepository.findAllByOrderEntityUuid(uuid)
                .stream()
                .map(product -> new DesiredProduct(product.getProductUuid(),
                        product.getQuantity(),
                        product.getPriceAtOrder()))
                .toList();

        return new Order(
                orderEntity.getUuid(),
                orderEntity.getUserUuid(),
                orderEntity.getOrderDate(),
                orderEntity.getStatus(),
                products
        );
    }

}
