package com.benjamin.parsy.ksb.stock.infrastructure.db.jpa.mapper;

import com.benjamin.parsy.ksb.stock.entity.model.Product;
import com.benjamin.parsy.ksb.stock.infrastructure.db.jpa.schema.ProductEntity;

public class ProductMapper {

    private ProductMapper() {
        // Private constructor for utility class
    }

    public static Product toProduct(ProductEntity productEntity) {

        return new Product(
                productEntity.getUuid(),
                productEntity.getName(),
                productEntity.getPrice()
        );

    }

}
