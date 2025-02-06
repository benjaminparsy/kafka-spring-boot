package com.benjamin.parsy.ksb.order.infrastructure.jpa;

import com.benjamin.parsy.ksb.order.domain.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.domain.model.Order;
import com.benjamin.parsy.ksb.order.infrastructure.InfrastructureTest;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.order.OrderJpaRepository;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.orderitem.OrderItemJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrderRepositoryJpaImplTest extends InfrastructureTest {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private OrderItemJpaRepository orderItemJpaRepository;

    @Autowired
    private OrderRepositoryJpaImpl orderRepositoryJpaImpl;

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
        orderRepositoryJpaImpl.save(order);

        // Then
        assertEquals(1, orderJpaRepository.findAll().size());
        assertEquals(2, orderItemJpaRepository.findAll().size());

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
        Optional<Order> orderOptional = orderRepositoryJpaImpl.findById(orderUuid);

        // Then
        assertTrue(orderOptional.isPresent());

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
        Optional<Order> orderOptional = orderRepositoryJpaImpl.findById(orderUuid);

        // Then
        assertTrue(orderOptional.isEmpty());

    }

}