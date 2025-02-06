package com.benjamin.parsy.ksb.order.application.kafka;

import com.benjamin.parsy.ksb.order.application.ApplicationTest;
import com.benjamin.parsy.ksb.order.application.kafka.model.StockReservedEvent;
import com.benjamin.parsy.ksb.order.domain.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.domain.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class StockKafkaConsumerTest extends ApplicationTest {

    @Autowired
    private StockKafkaConsumer stockKafkaConsumer;

    @MockitoBean
    private OrderService orderService;

    @Test
    void handleStockReserved_EventOk_ConfirmOrder() throws OrderNotFoundException {

        // Given
        UUID orderUuid = UUID.randomUUID();

        StockReservedEvent stockReservedEvent = new StockReservedEvent(orderUuid);

        doNothing()
                .when(orderService)
                .confirmOrder(orderUuid);

        // When
        stockKafkaConsumer.handleStockReserved(stockReservedEvent);

        // Then
        verify(orderService, times(1))
                .confirmOrder(any(UUID.class));

    }

}