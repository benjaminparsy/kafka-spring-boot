package com.benjamin.parsy.ksb.user.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.user.entity.event.UserEventPublisher;
import com.benjamin.parsy.ksb.user.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.user.entity.model.event.UserValidatedEvent;
import com.benjamin.parsy.ksb.user.infrastructure.configuration.KafkaConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaUserEventPublisher implements UserEventPublisher {

    private final KafkaTemplate<String, UserValidatedEvent> kafkaTemplateUserEvent;
    private final KafkaTemplate<String, OrderFailedEvent> kafkaTemplateOrderEvent;


    @Override
    public void publishUserValidated(UserValidatedEvent userValidatedEvent) {
        kafkaTemplateUserEvent.send(KafkaConstant.TOPIC_USER_VALIDATED, userValidatedEvent);
    }

    @Override
    public void publishOrderFailed(OrderFailedEvent orderFailedEvent) {
        kafkaTemplateOrderEvent.send(KafkaConstant.TOPIC_ORDER_FAILED, orderFailedEvent);
    }

}
