package com.benjamin.parsy.ksb.user.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.user.entity.event.EventPublisher;
import com.benjamin.parsy.ksb.user.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.user.entity.model.event.UserValidatedEvent;
import com.benjamin.parsy.ksb.user.infrastructure.configuration.KafkaConstant;
import com.benjamin.parsy.ksb.user.infrastructure.event.kafka.dto.OrderFailedKafkaEvent;
import com.benjamin.parsy.ksb.user.infrastructure.event.kafka.dto.UserValidatedKafkaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, OrderFailedKafkaEvent> orderFailedKafkaTemplate;
    private final KafkaTemplate<String, UserValidatedKafkaEvent> userValidatedKafkaTemplate;

    @Override
    public void publishOrderFailedEvent(OrderFailedEvent orderFailedEvent) {

        OrderFailedKafkaEvent orderFailedKafkaEvent = new OrderFailedKafkaEvent(
                orderFailedEvent.getOrderUuid().toString(),
                orderFailedEvent.getCause()
        );

        orderFailedKafkaTemplate.send(KafkaConstant.TOPIC_ORDER_FAILED, orderFailedKafkaEvent);

    }

    @Override
    public void publishUserValidatedEvent(UserValidatedEvent userValidatedEvent) {

        UserValidatedKafkaEvent userValidatedKafkaEvent = new UserValidatedKafkaEvent(
                userValidatedEvent.getOrderUuid().toString()
        );

        userValidatedKafkaTemplate.send(KafkaConstant.TOPIC_USER_VALIDATED, userValidatedKafkaEvent);

    }

}
