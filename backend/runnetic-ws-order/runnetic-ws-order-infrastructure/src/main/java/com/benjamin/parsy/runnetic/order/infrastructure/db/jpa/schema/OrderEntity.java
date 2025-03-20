package com.benjamin.parsy.runnetic.order.infrastructure.db.jpa.schema;

import com.benjamin.parsy.runnetic.order.entity.model.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "orders")
@Table(schema = "app", name = "orders")
public class OrderEntity extends BaseEntity {

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "user_uuid")
    private UUID userUuid;

    @Column(name = "order_date")
    private OffsetDateTime orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_price")
    private double totalPrice;

    public OrderEntity(Order order) {
        this.uuid = order.getUuid();
        this.userUuid = order.getUserUuid();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus().name();
        this.totalPrice = order.getTotalPrice();
    }

}
