package com.benjamin.parsy.ksb.order.order;

import com.benjamin.parsy.ksb.order.userprojection.UserProjection;
import com.benjamin.parsy.ksb.shared.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "orders")
@Table(schema = "app", name = "orders")
public class Order extends BaseEntity {

    @Column(name = "order_date")
    private OffsetDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatusEnum orderStatusEnum;

    @Column(name = "order_total")
    private int orderTotal;

    @ManyToOne(targetEntity = UserProjection.class)
    private UserProjection userProjection;

}
