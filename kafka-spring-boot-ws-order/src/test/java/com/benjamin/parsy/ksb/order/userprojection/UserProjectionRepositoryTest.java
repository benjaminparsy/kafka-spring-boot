package com.benjamin.parsy.ksb.order.userprojection;

import com.benjamin.parsy.ksb.order.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserProjectionRepositoryTest extends AbstractTest {

    @Autowired
    private UserProjectionRepository userProjectionRepository;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findByEmail_EmailExist_ReturnUser() {

        // Given
        UserProjection userProjection = userProjectionRepository.findAll().getFirst();

        // When
        Optional<UserProjection> userFindByEmail = userProjectionRepository.findByEmail(userProjection.getEmail());

        // Then
        assertTrue(userFindByEmail.isPresent());
        assertEquals(userProjection, userFindByEmail.get());

    }

}