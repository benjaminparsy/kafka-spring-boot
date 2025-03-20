package com.benjamin.parsy.runnetic.user.infrastructure.db.gateway;

import com.benjamin.parsy.runnetic.user.entity.exception.UserNotFoundException;
import com.benjamin.parsy.runnetic.user.infrastructure.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class JpaUserGatewayTest extends IntegrationTest {

    @Autowired
    private JpaUserGateway userRepositoryJpaImpl;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findByUuid_UserExist_ReturnUser() {

        // Given
        UUID orderUuid = UUID.fromString("56a99dec-d066-411f-9fa7-d525877c4117");

        // When and then
        assertDoesNotThrow(() -> userRepositoryJpaImpl.existsById(orderUuid));

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findByUuid_UserNotExist_ReturnEmpty() {

        // Given
        UUID orderUuid = UUID.randomUUID();

        // When and then
        assertThrows(UserNotFoundException.class,() -> userRepositoryJpaImpl.existsById(orderUuid));

    }

}