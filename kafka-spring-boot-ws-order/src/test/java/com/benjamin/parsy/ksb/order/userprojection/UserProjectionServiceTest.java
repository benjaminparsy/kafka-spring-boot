package com.benjamin.parsy.ksb.order.userprojection;

import com.benjamin.parsy.ksb.order.AbstractTest;
import com.benjamin.parsy.ksb.order.shared.exception.UserProjectionNotFoundException;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserProjectionServiceTest extends AbstractTest {

    @Autowired
    private UserProjectionService userProjectionService;

    @Autowired
    private UserProjectionRepository userProjectionRepository;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void updateUser_DataOk_UserUpdated() {

        // Given
        UserProjection user = Optional.of(userProjectionRepository.findAll().getFirst()).orElseThrow();

        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress();
        String address = faker.address().fullAddress();
        String phone = "0123456789";

        // When
        assertDoesNotThrow(() -> userProjectionService.updateUser(user.getId(), name, email, address, phone));

        // Then
        UserProjection userUpdated = userProjectionRepository.findById(user.getId()).orElseThrow();

        assertEquals(name, userUpdated.getName());
        assertEquals(email, userUpdated.getEmail());
        assertEquals(address, userUpdated.getAddress());
        assertEquals(phone, userUpdated.getPhone());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void updateUser_UserNotExist_ThrowException() {

        // Given
        long idUnknown = userProjectionRepository.findAll().getLast().getId() + 1;

        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().safeEmailAddress();
        String address = faker.address().fullAddress();
        String phone = "0123456789";

        // When and Then
        assertThrows(UserProjectionNotFoundException.class,
                () -> userProjectionService.updateUser(idUnknown, name, email, address, phone));

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @MethodSource("provideDataInvalid")
    @ParameterizedTest
    void updateUser_invalidData_ThrowException(String name, String email, String address, String phone) {

        // Given
        UserProjection user = Optional.of(userProjectionRepository.findAll().getFirst()).orElseThrow();

        // When and Then
        assertThrows(Exception.class,
                () -> userProjectionService.updateUser(user.getId(), name, email, address, phone));

    }

    private static Stream<Arguments> provideDataInvalid() {
        return Stream.of(
                // Name invalid
                Arguments.of(null, "john.smith@example.com", "8 Liberty Road, New-York", "+33123456789"),
                Arguments.of("a".repeat(300), "john.smith@example.com", "8 Liberty Road, New-York", "+33123456789"),
                // Email invalid
                Arguments.of("John Smith", null, "8 Liberty Road, New-York", "+33123456789"),
                Arguments.of("John Smith", "a".repeat(300) + "@example.com", "8 Liberty Road, New-York", "+33123456789"),
                // Address invalid
                Arguments.of("John Smith", "john.smith@example.com", "a".repeat(300), "+33123456789"),
                // Phone invalid
                Arguments.of("John Smith", "john.smith@example.com", "8 Liberty Road, New-York", "1".repeat(300))
        );
    }

}