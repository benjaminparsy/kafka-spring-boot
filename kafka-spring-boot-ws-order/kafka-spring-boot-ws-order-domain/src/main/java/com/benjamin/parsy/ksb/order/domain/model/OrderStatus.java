package com.benjamin.parsy.ksb.order.domain.model;

import java.util.Arrays;

public enum OrderStatus {

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

    public static OrderStatus safeValueOf(String value) {
        return Arrays.stream(OrderStatus.values())
                .filter(o -> o.toString().equals(value))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
