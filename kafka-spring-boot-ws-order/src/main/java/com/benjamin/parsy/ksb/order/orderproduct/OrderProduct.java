package com.benjamin.parsy.ksb.order.orderproduct;

import com.benjamin.parsy.ksb.order.order.Order;
import com.benjamin.parsy.ksb.order.stockprojection.StockProjection;
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
@Entity(name = "order_product")
@Table(name = "order_product")
public class OrderProduct extends BaseEntity {

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(targetEntity = Order.class)
    private Order order;

    @ManyToOne(targetEntity = StockProjection.class)
    private StockProjection stockProjection;

}
