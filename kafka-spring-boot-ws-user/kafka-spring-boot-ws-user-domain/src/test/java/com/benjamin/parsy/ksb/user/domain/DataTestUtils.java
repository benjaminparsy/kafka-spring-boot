package com.benjamin.parsy.ksb.user.domain;

import com.benjamin.parsy.ksb.user.domain.model.User;
import com.github.javafaker.Faker;

import java.util.UUID;

public final class DataTestUtils {

    private static final Faker FAKER;

    static {
        FAKER = new Faker();
    }

    private DataTestUtils() {
        // Private constructor for utility class
    }

    public static User createUser() {
        return new User(UUID.randomUUID(),
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.internet().safeEmailAddress(),
                FAKER.address().fullAddress(),
                FAKER.phoneNumber().phoneNumber()
        );
    }

}
