package com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.orderitem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {

    List<OrderItemEntity> findAllByOrderEntityUuid(UUID orderEntityUuid);

}
