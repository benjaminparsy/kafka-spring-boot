package com.benjamin.parsy.ksb.order.usecase;

import com.benjamin.parsy.ksb.order.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.order.entity.gateway.OrderGateway;
import com.benjamin.parsy.ksb.order.entity.model.Order;
import com.benjamin.parsy.ksb.order.entity.model.OrderStatus;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderCreatedEvent;
import com.benjamin.parsy.ksb.order.usecase.dto.IDesiredProductPublicData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateOrderUseCaseTest {

    private static OffsetDateTime datetimeBeforeTest;

    @InjectMocks
    private CreateOrderUseCase createOrderUseCase;

    @Mock
    private EventGateway eventGateway;

    @Mock
    private OrderGateway orderGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        datetimeBeforeTest = OffsetDateTime.now();
    }

    @Test
    void createOrder_AllOk_CreateOrder() {

        // Given
        UUID userUuid = UUID.randomUUID();

        List<IDesiredProductPublicData> products = List.of(
                DataTestUtils.createDesiredProductPublicData(),
                DataTestUtils.createDesiredProductPublicData()
        );

        when(orderGateway.save(any(Order.class)))
                .thenAnswer(i -> i.getArgument(0));

        // When
        Order order = Assertions.assertDoesNotThrow(() -> createOrderUseCase.createOrder(userUuid, products));

        // Then
        // Check order
        assertNotNull(order.getUuid());
        assertNotNull(order.getUserUuid());
        assertTrue(order.getOrderDate().isAfter(datetimeBeforeTest));
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(products.size(), order.getProducts().size());
        assertTrue(order.getTotalPrice() > 0);

        // Check order gateway
        verify(orderGateway, times(1)).save(any(Order.class));

        // Check order event publisher
        ArgumentCaptor<OrderCreatedEvent> orderEventCaptor = ArgumentCaptor.forClass(OrderCreatedEvent.class);
        verify(eventGateway, times(1)).publish(orderEventCaptor.capture());
        assertEquals(order.getUuid(), orderEventCaptor.getValue().getOrderUuid());

    }

}