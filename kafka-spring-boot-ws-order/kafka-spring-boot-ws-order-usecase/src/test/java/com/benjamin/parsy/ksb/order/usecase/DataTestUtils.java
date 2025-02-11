package com.benjamin.parsy.ksb.order.usecase;

import com.benjamin.parsy.ksb.order.entity.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.entity.model.Order;
import com.benjamin.parsy.ksb.order.usecase.dto.IDesiredProductPublicData;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.UUID;

public final class DataTestUtils {

    private DataTestUtils() {
        // Private constructor for utility class
    }

    public static Order createOrder() {
        return new Order(
                UUID.randomUUID(),
                List.of(
                        new DesiredProduct(
                                UUID.randomUUID(),
                                RandomUtils.secure().randomInt(1, 5),
                                RandomUtils.secure().randomDouble(10.00, 200.00)
                        ),
                        new DesiredProduct(
                                UUID.randomUUID(),
                                RandomUtils.secure().randomInt(1, 5),
                                RandomUtils.secure().randomDouble(10.00, 200.00)
                        )
                )
        );
    }

    public static IDesiredProductPublicData createDesiredProductPublicData() {
        return new IDesiredProductPublicData() {
            @Override
            public UUID productUuid() {
                return UUID.randomUUID();
            }

            @Override
            public int quantity() {
                return RandomUtils.secure().randomInt(1, 5);
            }

            @Override
            public double price() {
                return RandomUtils.secure().randomDouble(10.00, 200.00);
            }
        };
    }

}
