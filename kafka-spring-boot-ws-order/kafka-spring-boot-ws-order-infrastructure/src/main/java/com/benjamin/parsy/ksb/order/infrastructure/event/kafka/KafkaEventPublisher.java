package com.benjamin.parsy.ksb.order.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.order.entity.event.EventPublisher;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderCanceledEvent;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderConfirmedEvent;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderCreatedEvent;
import com.benjamin.parsy.ksb.order.infrastructure.configuration.KafkaConstant;
import com.benjamin.parsy.ksb.order.infrastructure.event.kafka.dto.DesiredProductKafkaEvent;
import com.benjamin.parsy.ksb.order.infrastructure.event.kafka.dto.OrderCanceledKafkaEvent;
import com.benjamin.parsy.ksb.order.infrastructure.event.kafka.dto.OrderConfirmedKafkaEvent;
import com.benjamin.parsy.ksb.order.infrastructure.event.kafka.dto.OrderCreatedKafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, OrderCreatedKafkaEvent> createdKafkaEventKafkaTemplate;
    private final KafkaTemplate<String, OrderCanceledKafkaEvent> canceledKafkaEventKafkaTemplate;
    private final KafkaTemplate<String, OrderConfirmedKafkaEvent> confirmedKafkaEventKafkaTemplate;

    @Override
    public void publishOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {

        List<DesiredProductKafkaEvent> desiredProductKafkaEventList = orderCreatedEvent.getProducts()
                .stream()
                .map(dp -> new DesiredProductKafkaEvent(
                        dp.getProductUuid().toString(),
                        dp.getQuantity(),
                        dp.getPrice()))
                .toList();

        OrderCreatedKafkaEvent orderCreatedKafkaEvent = new OrderCreatedKafkaEvent(
                orderCreatedEvent.getOrderUuid().toString(),
                orderCreatedEvent.getUserUuid().toString(),
                orderCreatedEvent.getOrderDate().toString(),
                orderCreatedEvent.getStatus().toString(),
                desiredProductKafkaEventList,
                orderCreatedEvent.getTotalPrice()
        );

        createdKafkaEventKafkaTemplate.send(KafkaConstant.TOPIC_ORDER_CREATED, orderCreatedKafkaEvent);

    }

    @Override
    public void publishOrderConfirmedEvent(OrderConfirmedEvent orderConfirmedEvent) {

        OrderConfirmedKafkaEvent orderConfirmedKafkaEvent = new OrderConfirmedKafkaEvent(
                orderConfirmedEvent.getOrderUuid().toString()
        );

        confirmedKafkaEventKafkaTemplate.send(KafkaConstant.TOPIC_ORDER_CONFIRMED, orderConfirmedKafkaEvent);

    }

    @Override
    public void publishOrderCanceledEvent(OrderCanceledEvent orderCanceledEvent) {

        OrderCanceledKafkaEvent orderCanceledKafkaEvent = new OrderCanceledKafkaEvent(
                orderCanceledEvent.getOrderUuid().toString(),
                orderCanceledEvent.getCause()
        );

        canceledKafkaEventKafkaTemplate.send(KafkaConstant.TOPIC_ORDER_CANCELED, orderCanceledKafkaEvent);

    }
}
