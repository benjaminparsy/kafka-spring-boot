package com.benjamin.parsy.ksb.order.stockprojection;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StockProjectionRepository extends JpaRepository<StockProjection, Long> {

    @Cacheable(value = "stockProjection", key = "#productIdCollection")
    List<StockProjection> findAllByProductIdIn(Collection<Long> productIdCollection);

    Optional<StockProjection> findByProductId(long productId);

    @CacheEvict(value = "stockProjection", allEntries = true)
    @Modifying
    @Transactional
    @Query("UPDATE stock_projections sp " +
            "SET sp.productName = :productName, " +
                "sp.price = :price, " +
                "sp.stockQuantity = :stockQuantity " +
            "WHERE sp.id = :productId")
    void updateStock(@Param("productId") long productId,
                     @Param("productName") String productName,
                     @Param("price") int price,
                     @Param("stockQuantity") int stockQuantity);

}
