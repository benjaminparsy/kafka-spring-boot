package com.benjamin.parsy.ksb.order.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.order.entity.event.EventPublisher;
import com.benjamin.parsy.ksb.order.entity.model.event.Event;
import com.benjamin.parsy.ksb.order.infrastructure.configuration.KafkaConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    public void publish(Event event) {

        String topics = switch (event.getType()) {
            case ORDER_PAID -> KafkaConstant.TOPIC_ORDER_PAID;
            case ORDER_FAILED -> KafkaConstant.TOPIC_ORDER_FAILED;
            case ORDER_CREATED -> KafkaConstant.TOPIC_ORDER_CREATED;
            case ORDER_CANCELED -> KafkaConstant.TOPIC_ORDER_CANCELED;
            case ORDER_CONFIRMED -> KafkaConstant.TOPIC_ORDER_CONFIRMED;
        };

        kafkaTemplate.send(topics, event);
    }

}
