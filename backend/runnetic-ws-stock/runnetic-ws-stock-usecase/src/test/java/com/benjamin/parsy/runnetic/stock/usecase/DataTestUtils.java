package com.benjamin.parsy.runnetic.stock.usecase;

import com.benjamin.parsy.runnetic.stock.usecase.dto.TestDesiredProductPublicData;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.UUID;

public final class DataTestUtils {

    private DataTestUtils() {
        // Private constructor for utility class
    }

    public static List<TestDesiredProductPublicData> createDesiredProducts() {
        return List.of(createDesiredProductPublicData(),
                createDesiredProductPublicData(),
                createDesiredProductPublicData()
        );
    }

    private static TestDesiredProductPublicData createDesiredProductPublicData() {
        return new TestDesiredProductPublicData(UUID.randomUUID(),
                RandomUtils.secure().randomInt(1, 5),
                RandomUtils.secure().randomDouble(10.00, 200.00)
        );
    }

}
