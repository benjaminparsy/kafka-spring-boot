package com.benjamin.parsy.runnetic.order.usecase;

import com.benjamin.parsy.runnetic.order.usecase.exception.OrderNotFoundException;
import com.benjamin.parsy.runnetic.order.usecase.port.EventPort;
import com.benjamin.parsy.runnetic.order.usecase.port.OrderPort;
import com.benjamin.parsy.runnetic.order.entity.model.Order;
import com.benjamin.parsy.runnetic.order.entity.model.OrderStatus;
import com.benjamin.parsy.runnetic.order.entity.model.event.OrderCanceledEvent;
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
    private OrderPort orderPort;

    @Mock
    private EventPort eventPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cancelOrder_AllOk_StatusCanceled() throws OrderNotFoundException {

        // Given
        String cause = "user invalid";
        Order order = DataTestUtils.createOrder();

        when(orderPort.findById(order.getUuid()))
                .thenReturn(order);

        // When
        assertDoesNotThrow(() -> cancelOrderUseCase.cancelOrder(order.getUuid(), cause));

        // Then
        // Check order gateway
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderPort, times(1)).update(orderCaptor.capture());
        assertEquals(OrderStatus.CANCELED, orderCaptor.getValue().getStatus());

        // Check order event publisher
        ArgumentCaptor<OrderCanceledEvent> orderCanceledEventCaptor = ArgumentCaptor.forClass(OrderCanceledEvent.class);
        verify(eventPort, times(1)).publish(orderCanceledEventCaptor.capture());
        assertEquals(order.getUuid(), orderCanceledEventCaptor.getValue().getOrderUuid());

    }

}
