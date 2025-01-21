package com.benjamin.parsy.ksb.order.application.kafka;

import com.benjamin.parsy.ksb.order.application.ApplicationTest;
import com.benjamin.parsy.ksb.order.application.kafka.model.OrderFailedEvent;
import com.benjamin.parsy.ksb.order.domain.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.domain.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderKafkaConsumerTest extends ApplicationTest {

    @Autowired
    private OrderKafkaConsumer orderKafkaConsumer;

    @MockitoBean
    private OrderService orderService;

    @Test
    void handleOrderFailed_EventOk_CancelOrder() throws OrderNotFoundException {

        // Given
        UUID orderUuid = UUID.randomUUID();
        String cause = "user invalid";

        OrderFailedEvent orderFailedEvent = new OrderFailedEvent(orderUuid, cause);

        doNothing()
                .when(orderService)
                .cancelOrder(orderUuid, cause);

        // When
        orderKafkaConsumer.handleOrderFailed(orderFailedEvent);

        // Then
        verify(orderService, times(1))
                .cancelOrder(any(UUID.class), anyString());

    }

}