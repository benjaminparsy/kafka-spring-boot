package com.benjamin.parsy.runnetic.order.infrastructure.db.jpa.port;

import com.benjamin.parsy.runnetic.order.usecase.exception.OrderNotFoundException;
import com.benjamin.parsy.runnetic.order.entity.model.DesiredProduct;
import com.benjamin.parsy.runnetic.order.entity.model.Order;
import com.benjamin.parsy.runnetic.order.infrastructure.IntegrationTest;
import com.benjamin.parsy.runnetic.order.infrastructure.db.jpa.repository.OrderItemRepository;
import com.benjamin.parsy.runnetic.order.infrastructure.db.jpa.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaOrderPortTest extends IntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private JpaOrderPort jpaOrderGateway;

    @Sql(scripts = {
            "classpath:purge.sql",
    })
    @Test
    void save() {

        // Given
        UUID userUuid = UUID.randomUUID();
        List<DesiredProduct> products = List.of(
                new DesiredProduct(UUID.randomUUID(), 2, 100.00),
                new DesiredProduct(UUID.randomUUID(), 1, 75.00)
        );

        Order order = new Order(userUuid, products);

        // When
        jpaOrderGateway.save(order);

        // Then
        assertEquals(1, orderRepository.findAll().size());
        assertEquals(2, orderItemRepository.findAll().size());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findByUuid_UserExist_ReturnUser() {

        // Given
        UUID orderUuid = UUID.fromString("56a99dec-d066-411f-9fa7-d525877c4117");

        // When
        Order order = Assertions.assertDoesNotThrow(() -> jpaOrderGateway.findById(orderUuid));

        // Then
        assertNotNull(order);

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
        OrderNotFoundException ex = assertThrows(OrderNotFoundException.class, () -> jpaOrderGateway.findById(orderUuid));

        // Then
        assertFalse(ex.getMessage().isEmpty());

    }

}