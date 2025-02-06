package com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.order;

import java.util.Arrays;

public enum OrderStatusEnum {

    /**
     * Order awaiting validation (stock, payment...)
     */
    PENDING,

    /**
     * Order validated after all checks.
     */
    CONFIRMED,

    /**
     * Order cancelled (insufficient stock, invalid user...)
     */
    CANCELED,

    /**
     * Order shipped
     */
    SHIPPED,

    /**
     * Order delivered
     */
    DELIVERED,

    UNKNOWN;

    public static OrderStatusEnum safeValueOf(String value) {

        return Arrays.stream(OrderStatusEnum.values())
                .filter(o -> o.toString().equals(value))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
