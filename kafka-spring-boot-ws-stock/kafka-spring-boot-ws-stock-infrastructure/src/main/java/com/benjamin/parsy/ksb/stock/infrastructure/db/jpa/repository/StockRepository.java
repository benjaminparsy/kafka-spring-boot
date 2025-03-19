package com.benjamin.parsy.ksb.stock.infrastructure.db.jpa.repository;

import com.benjamin.parsy.ksb.stock.infrastructure.db.jpa.schema.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<StockEntity, Long> {

    @Modifying
    @Query("UPDATE stocks " +
            "SET quantity = :quantity " +
            "WHERE uuid = :uuid")
    void update(@Param("quantity") int quantity,
                @Param("uuid") UUID uuid);

    Optional<StockEntity> findByProductUuid(UUID productUuid);

}
