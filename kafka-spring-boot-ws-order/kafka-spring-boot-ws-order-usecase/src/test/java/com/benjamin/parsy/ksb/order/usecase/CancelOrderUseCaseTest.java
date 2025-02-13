package com.benjamin.parsy.ksb.order.usecase;

import com.benjamin.parsy.ksb.order.entity.event.EventPublisher;
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
import static org.mockito.Mockito.*;

class CancelOrderUseCaseTest {

    @InjectMocks
    private CancelOrderUseCase cancelOrderUseCase;

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
    void cancelOrder_AllOk_StatusCanceled() throws OrderNotFoundException {

        // Given
        String cause = "user invalid";
        Order order = DataTestUtils.createOrder();

        when(orderGateway.findById(order.getUuid()))
                .thenReturn(order);

        // When
        assertDoesNotThrow(() -> cancelOrderUseCase.cancelOrder(order.getUuid(), cause));

        // Then
        // Check order gateway
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderGateway, times(1)).update(orderCaptor.capture());
        assertEquals(OrderStatus.CANCELED, orderCaptor.getValue().getStatus());

        // Check event gateway
        ArgumentCaptor<OrderCanceledEvent> orderCanceledEventCaptor = ArgumentCaptor.forClass(OrderCanceledEvent.class);
        verify(eventGateway, times(1)).save(orderCanceledEventCaptor.capture());
        assertEquals(order.getUuid(), orderCanceledEventCaptor.getValue().getOrderUuid());

        // Check order event publisher
        verify(eventPublisher, times(1)).publishOrderCanceledEvent(orderCanceledEventCaptor.capture());
        assertEquals(order.getUuid(), orderCanceledEventCaptor.getValue().getOrderUuid());

    }

}
