package com.benjamin.parsy.ksb.order.infrastructure.kafka;

import com.benjamin.parsy.ksb.order.domain.model.event.OrderCanceledEvent;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderConfirmedEvent;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderCreatedEvent;
import com.benjamin.parsy.ksb.order.domain.publisher.OrderEventPublisher;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.persistence.kafkaevent.KafkaEventService;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.OrderKafkaEvent;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.ordercanceledevent.OrderCanceledEventAdapter;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.ordercanceledevent.OrderCanceledKafkaEvent;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.orderconfirmedevent.OrderConfirmedEventAdapter;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.orderconfirmedevent.OrderConfirmedKafkaEvent;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.ordercreatedevent.OrderCreatedEventAdapter;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.ordercreatedevent.OrderCreatedKafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventKafkaPublisher implements OrderEventPublisher {

    private final OrderCreatedEventAdapter orderCreatedEventAdapter;
    private final OrderCanceledEventAdapter orderCanceledEventAdapter;
    private final OrderConfirmedEventAdapter orderConfirmedEventAdapter;
    private final KafkaTemplate<String, OrderKafkaEvent> kafkaTemplate;
    private final KafkaEventService kafkaEventService;

    @Override
    public void publishCreatedOrder(OrderCreatedEvent event) {

        kafkaEventService.saveEvent(event, KafkaConstant.TOPIC_ORDER_CREATED);

        OrderCreatedKafkaEvent orderCreatedKafkaEvent = orderCreatedEventAdapter.toKafkaEvent(event);
        kafkaTemplate.send(KafkaConstant.TOPIC_ORDER_CREATED, orderCreatedKafkaEvent);
    }

    @Override
    public void publishCanceledOrder(OrderCanceledEvent event) {

        kafkaEventService.saveEvent(event, KafkaConstant.TOPIC_ORDER_CANCELED);

        OrderCanceledKafkaEvent orderCanceledKafkaEvent = orderCanceledEventAdapter.toKafkaEvent(event);
        kafkaTemplate.send(KafkaConstant.TOPIC_ORDER_CANCELED, orderCanceledKafkaEvent);
    }

    @Override
    public void publishConfirmedOrder(OrderConfirmedEvent event) {

        kafkaEventService.saveEvent(event, KafkaConstant.TOPIC_ORDER_CONFIRMED);

        OrderConfirmedKafkaEvent orderConfirmedKafkaEvent = orderConfirmedEventAdapter.toKafkaEvent(event);
        kafkaTemplate.send(KafkaConstant.TOPIC_ORDER_CONFIRMED, orderConfirmedKafkaEvent);
    }

}
