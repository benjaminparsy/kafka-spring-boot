package com.benjamin.parsy.ksb.order.usecase;

import com.benjamin.parsy.ksb.order.entity.event.EventPublisher;
import com.benjamin.parsy.ksb.order.entity.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.order.entity.gateway.OrderGateway;
import com.benjamin.parsy.ksb.order.entity.model.Order;
import com.benjamin.parsy.ksb.order.entity.model.OrderStatus;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderConfirmedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConfirmedOrderUseCaseTest {

    @InjectMocks
    private ConfirmedOrderUseCase confirmedOrderUseCase;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private EventGateway eventGateway;

    @Mock
    private EventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void confirmOrder_AllOk_StatusConfirmed() throws OrderNotFoundException {

        // Given
        Order order = DataTestUtils.createOrder();

        when(orderGateway.findById(order.getUuid()))
                .thenReturn(order);

        // When
        assertDoesNotThrow(() -> confirmedOrderUseCase.confirmOrder(order.getUuid()));

        // Then
        checkOrderGateway();
        checkEventGateway(order);
        checkOrderEventPublisher(order);

    }


    private void checkOrderGateway() {

        verify(orderGateway, times(1))
                .save(any(Order.class));

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderGateway).save(orderCaptor.capture());
        assertEquals(OrderStatus.CONFIRMED, orderCaptor.getValue().getStatus());

    }

    private void checkEventGateway(Order order) {

        verify(eventGateway, times(1))
                .save(any(OrderConfirmedEvent.class));

        ArgumentCaptor<OrderConfirmedEvent> orderEventCaptor = ArgumentCaptor.forClass(OrderConfirmedEvent.class);
        verify(eventGateway).save(orderEventCaptor.capture());
        assertEquals(order.getUuid(), orderEventCaptor.getValue().getUuid());

    }

    private void checkOrderEventPublisher(Order order) {

        verify(eventPublisher, times(1))
                .publish(any(OrderConfirmedEvent.class));

        ArgumentCaptor<OrderConfirmedEvent> orderEventCaptor = ArgumentCaptor.forClass(OrderConfirmedEvent.class);
        verify(eventPublisher).publish(orderEventCaptor.capture());
        assertEquals(order.getUuid(), orderEventCaptor.getValue().getUuid());

    }

}
