package com.benjamin.parsy.ksb.user.application.kafka;

import com.benjamin.parsy.ksb.user.domain.service.UserService;
import com.benjamin.parsy.ksb.user.application.ApplicationTest;
import com.benjamin.parsy.ksb.user.application.kafka.model.OrderCreatedKafkaEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderKafkaConsumerTest extends ApplicationTest {

    @Autowired
    private OrderKafkaConsumer orderKafkaConsumer;

    @MockitoBean
    private UserService userService;

    @Test
    void handleOrderFailed_EventOk_CancelOrder() {

        // Given
        OrderCreatedKafkaEvent orderCreatedKafkaEvent = new OrderCreatedKafkaEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                OffsetDateTime.now().toString(),
                "PENDING",
                List.of(
                        new OrderCreatedKafkaEvent.Product(UUID.randomUUID(), 1, 50.00),
                        new OrderCreatedKafkaEvent.Product(UUID.randomUUID(), 2, 75)
                ),
                200.00
        );

        doNothing()
                .when(userService)
                .validateUser(orderCreatedKafkaEvent.orderUuid(), orderCreatedKafkaEvent.userUuid());

        // When
        orderKafkaConsumer.handleOrderCreated(orderCreatedKafkaEvent);

        // Then
        verify(userService, times(1))
                .validateUser(any(UUID.class), any(UUID.class));

    }

}