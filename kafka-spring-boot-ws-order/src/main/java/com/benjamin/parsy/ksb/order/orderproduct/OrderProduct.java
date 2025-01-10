package com.benjamin.parsy.ksb.order.orderproduct;

import com.benjamin.parsy.ksb.order.order.Order;
import com.benjamin.parsy.ksb.shared.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "order_products")
@Table(schema = "app", name = "order_products")
public class OrderProduct extends BaseEntity {

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(targetEntity = Order.class)
    private Order order;

    @Column(name = "product_id")
    private Long productId;

}
