package com.benjamin.parsy.runnetic.order.infrastructure.db.jpa.repository;

import com.benjamin.parsy.runnetic.order.infrastructure.db.jpa.schema.OrderEntity;
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
            "SET o.status = :status, " +
            "o.totalPrice = :totalPrice " +
            "WHERE o.uuid = :uuid")
    void updateByUuid(@Param("status") String status,
                      @Param("totalPrice") double totalPrice,
                      @Param("uuid") UUID uuid);

}
