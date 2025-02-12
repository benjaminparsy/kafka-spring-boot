package com.benjamin.parsy.ksb.user.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.user.entity.event.EventPublisher;
import com.benjamin.parsy.ksb.user.entity.model.event.Event;
import com.benjamin.parsy.ksb.user.infrastructure.configuration.KafkaConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    public void publish(Event event) {

        String topics = switch (event.getType()) {
            case ORDER_CREATED -> KafkaConstant.TOPIC_ORDER_CREATED;
            case ORDER_FAILED -> KafkaConstant.TOPIC_ORDER_FAILED;
            case USER_VALIDATED -> KafkaConstant.TOPIC_USER_VALIDATED;
        };

        kafkaTemplate.send(topics, event);

    }

}
