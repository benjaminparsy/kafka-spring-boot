package com.benjamin.parsy.ksb.order.domain;

import com.benjamin.parsy.ksb.order.domain.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.domain.model.Order;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.UUID;

public final class DataTestUtils {

    private DataTestUtils() {
        // Private constructor for utility class
    }

    public static Order createOrder() {
        return new Order(UUID.randomUUID(),
                List.of(
                        createDesiredProduct(),
                        createDesiredProduct()
                ));
    }

    public static DesiredProduct createDesiredProduct() {
        return new DesiredProduct(UUID.randomUUID(),
                RandomUtils.secure().randomInt(1, 5),
                RandomUtils.secure().randomDouble(10.00, 200.00));
    }


}
