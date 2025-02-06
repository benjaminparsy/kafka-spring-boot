package com.benjamin.parsy.ksb.order.infrastructure.jpa;

import com.benjamin.parsy.ksb.order.domain.model.Order;
import com.benjamin.parsy.ksb.order.domain.repository.OrderRepository;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.order.OrderEntity;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.order.OrderJpaAdapter;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.order.OrderJpaRepository;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.orderitem.OrderItemEntity;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.orderitem.OrderItemJpaAdapter;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.orderitem.OrderItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderRepositoryJpaImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderJpaAdapter orderAdapter;
    private final OrderItemJpaAdapter orderItemAdapter;

    @Override
    public Order save(Order order) {

        OrderEntity orderEntity = orderAdapter.toEntity(order);
        orderJpaRepository.save(orderEntity);

        List<OrderItemEntity> orderItemEntityList = orderItemAdapter.toEntity(order.getProducts(), orderEntity);
        orderItemJpaRepository.saveAll(orderItemEntityList);

        return orderAdapter.toDomainModel(orderEntity, orderItemEntityList);
    }

    @Override
    public Optional<Order> findById(UUID uuid) {

        Optional<OrderEntity> orderEntityOptional = orderJpaRepository.findByUuid(uuid);
        List<OrderItemEntity> orderItemEntityList = orderItemJpaRepository.findAllByOrderEntityUuid(uuid);

        return orderEntityOptional.map(orderEntity -> orderAdapter.toDomainModel(orderEntity, orderItemEntityList));

    }

}
