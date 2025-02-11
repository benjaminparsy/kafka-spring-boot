package com.benjamin.parsy.ksb.order.usecase;

import com.benjamin.parsy.ksb.order.entity.event.OrderEventPublisher;
import com.benjamin.parsy.ksb.order.entity.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.order.entity.gateway.OrderGateway;
import com.benjamin.parsy.ksb.order.entity.model.Order;
import com.benjamin.parsy.ksb.order.entity.model.OrderStatus;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderCanceledEvent;
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

class CancelOrderUseCaseTest {

    @InjectMocks
    private CancelOrderUseCase cancelOrderUseCase;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private EventGateway eventGateway;

    @Mock
    private OrderEventPublisher orderEventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cancelOrder_AllOk_StatusCanceled() throws OrderNotFoundException {

        // Given
        String cause = "user invalid";
        Order order = DataTestUtils.createOrder();

        when(orderGateway.findById(order.getUuid()))
                .thenReturn(order);

        // When
        assertDoesNotThrow(() -> cancelOrderUseCase.cancelOrder(order.getUuid(), cause));

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
        assertEquals(OrderStatus.CANCELED, orderCaptor.getValue().getStatus());

    }

    private void checkEventGateway(Order order) {

        verify(eventGateway, times(1))
                .save(any(OrderCanceledEvent.class));

        ArgumentCaptor<OrderCanceledEvent> orderCanceledEventCaptor = ArgumentCaptor.forClass(OrderCanceledEvent.class);
        verify(eventGateway).save(orderCanceledEventCaptor.capture());
        assertEquals(order.getUuid(), orderCanceledEventCaptor.getValue().getUuid());

    }

    private void checkOrderEventPublisher(Order order) {

        verify(orderEventPublisher, times(1))
                .publish(any(OrderCanceledEvent.class));

        ArgumentCaptor<OrderCanceledEvent> orderEventCaptor = ArgumentCaptor.forClass(OrderCanceledEvent.class);
        verify(orderEventPublisher).publish(orderEventCaptor.capture());
        assertEquals(order.getUuid(), orderEventCaptor.getValue().getUuid());

    }

}
