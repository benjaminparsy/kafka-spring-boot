package com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.schema;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "stocks")
@Table(schema = "app", name = "stocks")
public class StockEntity extends BaseEntity {

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(targetEntity = ProductEntity.class)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

}
