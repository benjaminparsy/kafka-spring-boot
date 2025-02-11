package com.benjamin.parsy.ksb.order.infrastructure.db.jpa.repository;

import com.benjamin.parsy.ksb.order.infrastructure.db.jpa.schema.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    List<OrderItemEntity> findAllByOrderEntityUuid(UUID orderEntityUuid);

}
