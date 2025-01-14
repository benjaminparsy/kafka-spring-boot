package com.benjamin.parsy.ksb.order.stockprojection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface StockProjectionRepository extends JpaRepository<StockProjection, Long> {

    @Cacheable(value = "stockProjection", key = "#productIdCollection")
    List<StockProjection> findAllByProductIdIn(Collection<Long> productIdCollection);

}
