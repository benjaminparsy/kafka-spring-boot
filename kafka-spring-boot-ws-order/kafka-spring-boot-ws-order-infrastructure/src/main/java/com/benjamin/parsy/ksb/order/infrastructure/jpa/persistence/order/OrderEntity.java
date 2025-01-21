package com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.order;

import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.BaseEntity;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatusEnum orderStatusEnum;

    @Column(name = "total_price")
    private double totalPrice;

}
