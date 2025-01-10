package com.benjamin.parsy.ksb.order.order;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatusEnum {

    CREATED,
    PROCESSING,
    CANCELLED,
    SHIPPED;

    public static Optional<OrderStatusEnum> safeValueOf(String value) {

        return Arrays.stream(OrderStatusEnum.values())
                .filter(o -> o.toString().equals(value))
                .findFirst();
    }

}
