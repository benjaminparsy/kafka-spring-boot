package com.benjamin.parsy.ksb.order.usecase;

import com.benjamin.parsy.ksb.order.entity.event.EventPublisher;
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
    private EventPublisher eventPublisher;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private EventGateway eventGateway;

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

        doNothing().when(eventPublisher)
                .publish(any(OrderCreatedEvent.class));


        // When
        Order order = Assertions.assertDoesNotThrow(() -> createOrderUseCase.createOrder(userUuid, products));

        // Then
        verify(eventPublisher, times(1))
                .publish(any(OrderCreatedEvent.class));

        checkOrder(order, products);
        checkOrderGateway();
        checkEventGateway(order);
        checkOrderEventPublisher(order);

    }

    private void checkOrder(Order order, List<IDesiredProductPublicData> products) {

        assertNotNull(order.getUuid());
        assertNotNull(order.getUserUuid());
        assertTrue(order.getOrderDate().isAfter(datetimeBeforeTest));
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(products.size(), order.getProducts().size());
        assertTrue(order.getTotalPrice() > 0);

    }

    private void checkOrderGateway() {

        verify(orderGateway, times(1))
                .save(any(Order.class));

    }

    private void checkEventGateway(Order order) {

        verify(eventGateway, times(1))
                .save(any(OrderCreatedEvent.class));

        ArgumentCaptor<OrderCreatedEvent> orderEventCaptor = ArgumentCaptor.forClass(OrderCreatedEvent.class);
        verify(eventGateway).save(orderEventCaptor.capture());
        assertEquals(order.getUuid(), orderEventCaptor.getValue().getUuid());

    }

    private void checkOrderEventPublisher(Order order) {

        verify(eventPublisher, times(1))
                .publish(any(OrderCreatedEvent.class));

        ArgumentCaptor<OrderCreatedEvent> orderEventCaptor = ArgumentCaptor.forClass(OrderCreatedEvent.class);
        verify(eventPublisher).publish(orderEventCaptor.capture());
        assertEquals(order.getUuid(), orderEventCaptor.getValue().getUuid());

    }

}