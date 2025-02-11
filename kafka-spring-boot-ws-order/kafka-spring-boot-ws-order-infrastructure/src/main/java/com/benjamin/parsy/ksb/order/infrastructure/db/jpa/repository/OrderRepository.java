package com.benjamin.parsy.ksb.order.infrastructure.db.jpa.repository;

import com.benjamin.parsy.ksb.order.infrastructure.db.jpa.schema.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByUuid(UUID uuid);

}
