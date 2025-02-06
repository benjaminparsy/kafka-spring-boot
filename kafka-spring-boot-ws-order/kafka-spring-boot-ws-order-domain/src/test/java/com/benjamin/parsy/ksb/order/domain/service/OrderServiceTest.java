package com.benjamin.parsy.ksb.order.domain.service;

import com.benjamin.parsy.ksb.order.domain.DataTestUtils;
import com.benjamin.parsy.ksb.order.domain.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.domain.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.domain.model.Order;
import com.benjamin.parsy.ksb.order.domain.model.OrderStatus;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderCanceledEvent;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderConfirmedEvent;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderCreatedEvent;
import com.benjamin.parsy.ksb.order.domain.publisher.OrderEventPublisher;
import com.benjamin.parsy.ksb.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderEventPublisher orderEventPublisher;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_AllOk_CreateOrder() {

        // Given
        UUID userUuid = UUID.randomUUID();

        List<DesiredProduct> products = List.of(
                DataTestUtils.createDesiredProduct(),
                DataTestUtils.createDesiredProduct()
        );

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(i -> i.getArgument(0));

        doNothing().when(orderEventPublisher)
                .publishCreatedOrder(any(OrderCreatedEvent.class));

        OffsetDateTime dateBeforeTest = OffsetDateTime.now();

        // When
        Order orderReturned = assertDoesNotThrow(() -> orderService.createOrder(userUuid, products));

        // Then
        verify(orderRepository, times(1))
                .save(any(Order.class));

        verify(orderEventPublisher, times(1))
                .publishCreatedOrder(any(OrderCreatedEvent.class));

        double totalPriceExpected = products.stream()
                .mapToDouble(entry -> entry.price() * entry.quantity())
                .sum();

        assertNotNull(orderReturned.getUuid());
        assertNotNull(orderReturned.getUserUuid());
        assertTrue(orderReturned.getOrderDate().isAfter(dateBeforeTest));
        assertEquals(OrderStatus.PENDING, orderReturned.getStatus());
        assertEquals(products.size(), orderReturned.getProducts().size());
        assertEquals(totalPriceExpected, orderReturned.getTotalPrice());

    }

    @Test
    void confirmOrder_AllOk_StatusConfirmed() {

        // Given
        Order order = DataTestUtils.createOrder();

        when(orderRepository.findById(order.getUuid()))
                .thenReturn(Optional.of(order));

        // When
        assertDoesNotThrow(() -> orderService.confirmOrder(order.getUuid()));

        // Then
        verify(orderRepository, times(1))
                .findById(any(UUID.class));

        verify(orderRepository, times(1))
                .save(any(Order.class));

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        assertEquals(OrderStatus.CONFIRMED, orderCaptor.getValue().getStatus());

        ArgumentCaptor<OrderConfirmedEvent> orderConfirmedEventCaptor = ArgumentCaptor.forClass(OrderConfirmedEvent.class);
        verify(orderEventPublisher).publishConfirmedOrder(orderConfirmedEventCaptor.capture());
        assertEquals(order.getUuid(), orderConfirmedEventCaptor.getValue().uuid());

    }

    @Test
    void cancelOrder_AllOk_StatusCanceled() {

        // Given
        String cause = "user invalid";
        Order order = DataTestUtils.createOrder();

        when(orderRepository.findById(order.getUuid()))
                .thenReturn(Optional.of(order));

        // When
        assertDoesNotThrow(() -> orderService.cancelOrder(order.getUuid(), cause));

        // Then
        verify(orderRepository, times(1))
                .findById(any(UUID.class));

        verify(orderRepository, times(1))
                .save(any(Order.class));

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        assertEquals(OrderStatus.CANCELED, orderCaptor.getValue().getStatus());

        ArgumentCaptor<OrderCanceledEvent> orderCanceledEventCaptor = ArgumentCaptor.forClass(OrderCanceledEvent.class);
        verify(orderEventPublisher).publishCanceledOrder(orderCanceledEventCaptor.capture());
        assertEquals(order.getUuid(), orderCanceledEventCaptor.getValue().uuid());

    }

    @Test
    void findOrder_OrderFind_ReturnOrder() {

        // Given
        Order order = DataTestUtils.createOrder();

        when(orderRepository.findById(order.getUuid()))
                .thenReturn(Optional.of(order));

        // When
        Order orderReturned = assertDoesNotThrow(() -> orderService.findOrder(order.getUuid()));

        // Then
        verify(orderRepository, times(1))
                .findById(any(UUID.class));

        assertEquals(order, orderReturned);

    }

    @Test
    void findOrder_OrderNotFound_ThrowException() {

        // Given
        UUID orderUuid = UUID.randomUUID();

        when(orderRepository.findById(orderUuid))
                .thenReturn(Optional.empty());

        // When
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class,
                () -> orderService.findOrder(orderUuid));

        // Then
        verify(orderRepository, times(1))
                .findById(any(UUID.class));

        assertEquals("Order not found for id " + orderUuid, exception.getMessage());

    }

}