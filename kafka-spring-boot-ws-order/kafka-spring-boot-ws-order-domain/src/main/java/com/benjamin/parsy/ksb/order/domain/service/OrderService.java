package com.benjamin.parsy.ksb.order.domain.service;

import com.benjamin.parsy.ksb.order.domain.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.domain.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.domain.model.Order;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderCanceledEvent;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderConfirmedEvent;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderCreatedEvent;
import com.benjamin.parsy.ksb.order.domain.publisher.OrderEventPublisher;
import com.benjamin.parsy.ksb.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    /**
     * Create a new command and publishes an order creation event
     *
     * @param userUuid User identifier
     * @param products Products desired
     * @return The new order created in PENDING state
     */
    public Order createOrder(UUID userUuid, List<DesiredProduct> products) {

        // 1. create and save order
        Order order = new Order(userUuid, products);
        order = orderRepository.save(order);

        // 2. Publish an order creation event
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order);
        orderEventPublisher.publishCreatedOrder(orderCreatedEvent);

        return order;
    }

    /**
     * To confirm an order and publish an order confirmation event
     *
     * @param orderUuid Order identifier
     * @throws OrderNotFoundException Throw if the command is not found with the identifier
     */
    public void confirmOrder(UUID orderUuid) throws OrderNotFoundException {

        Order order = findOrder(orderUuid);
        order.confirm();
        orderRepository.save(order);

        OrderConfirmedEvent orderConfirmedEvent = new OrderConfirmedEvent(order.getUuid());
        orderEventPublisher.publishConfirmedOrder(orderConfirmedEvent);

    }

    /**
     * To cancel an order and publish an order cancel event
     *
     * @param orderUuid Order identifier
     * @param cause     Cause of cancellation
     * @throws OrderNotFoundException Throw if the command is not found with the identifier
     */
    public void cancelOrder(UUID orderUuid, String cause) throws OrderNotFoundException {

        Order order = findOrder(orderUuid);
        order.cancel();
        orderRepository.save(order);

        OrderCanceledEvent orderCanceledEvent = new OrderCanceledEvent(order.getUuid(), cause);
        orderEventPublisher.publishCanceledOrder(orderCanceledEvent);

    }

    /**
     * To search for an order by its identifier
     *
     * @param orderUuid Order identifier
     * @return The order found
     * @throws OrderNotFoundException Throw if the command is not found with the identifier
     */
    protected Order findOrder(UUID orderUuid) throws OrderNotFoundException {

        Optional<Order> optionalOrder = orderRepository.findById(orderUuid);

        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException("Order not found for id " + orderUuid);
        }

        return optionalOrder.get();
    }

}
