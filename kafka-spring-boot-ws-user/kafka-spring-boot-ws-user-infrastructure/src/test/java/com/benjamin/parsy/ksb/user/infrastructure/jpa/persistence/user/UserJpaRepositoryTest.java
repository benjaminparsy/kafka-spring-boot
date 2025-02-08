package com.benjamin.parsy.ksb.user.infrastructure.jpa.persistence.user;

import com.benjamin.parsy.ksb.user.infrastructure.InfrastructureTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserJpaRepositoryTest extends InfrastructureTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findByUuid_UserExist_ReturnUser() {

        // Given
        UUID orderUuid = UUID.fromString("56a99dec-d066-411f-9fa7-d525877c4117");

        // When
        Optional<UserEntity> orderEntityOptional = userJpaRepository.findByUuid(orderUuid);

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
        Optional<UserEntity> orderEntityOptional = userJpaRepository.findByUuid(orderUuid);

        // Then
        assertTrue(orderEntityOptional.isEmpty());

    }

}