package com.benjamin.parsy.ksb.user.infrastructure.db.jpa.repository;

import com.benjamin.parsy.ksb.user.infrastructure.db.jpa.schema.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findByUuid_UserExist_ReturnUser() {

        // Given
        UUID orderUuid = UUID.fromString("56a99dec-d066-411f-9fa7-d525877c4117");

        // When
        Optional<UserEntity> orderEntityOptional = userRepository.findByUuid(orderUuid);

        // Then
        assertTrue(orderEntityOptional.isPresent());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findByUuid_UserNotExist_ReturnEmpty() {

        // Given
        UUID orderUuid = UUID.randomUUID();

        // When
        Optional<UserEntity> orderEntityOptional = userRepository.findByUuid(orderUuid);

        // Then
        assertTrue(orderEntityOptional.isEmpty());

    }

}