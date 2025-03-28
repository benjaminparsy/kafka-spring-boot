package com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.repository;

import com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.schema.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
