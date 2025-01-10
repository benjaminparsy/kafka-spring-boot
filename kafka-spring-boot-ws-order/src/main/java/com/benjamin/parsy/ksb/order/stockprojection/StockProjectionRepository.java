package com.benjamin.parsy.ksb.order.stockprojection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface StockProjectionRepository extends JpaRepository<StockProjection, Long> {

    List<StockProjection> findAllByProductIdIn(Collection<Long> productIdCollection);

}
