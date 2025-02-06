package com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.orderitem;

import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.BaseEntity;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.order.OrderEntity;
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
@Entity(name = "order_items")
@Table(schema = "app", name = "order_items")
public class OrderItemEntity extends BaseEntity {

    @Column(name = "product_uuid")
    private UUID productUuid;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price_at_order")
    private double priceAtOrder;

    @ManyToOne(targetEntity = OrderEntity.class)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

}
