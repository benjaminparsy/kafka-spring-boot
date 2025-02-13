package com.benjamin.parsy.ksb.order.infrastructure.db.jpa.repository;

import com.benjamin.parsy.ksb.order.infrastructure.db.jpa.schema.OrderEntity;
import com.benjamin.parsy.ksb.order.infrastructure.db.jpa.schema.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByUuid(UUID uuid);

    @Modifying
    @Query(value = "UPDATE orders o " +
            "SET o.orderStatusEnum = :orderStatusEnum, " +
            "o.totalPrice = :totalPrice " +
            "WHERE o.uuid = :uuid")
    void updateByUuid(@Param("orderStatusEnum") OrderStatusEnum orderStatusEnum,
                      @Param("totalPrice") double totalPrice,
                      @Param("uuid") UUID uuid);

}
