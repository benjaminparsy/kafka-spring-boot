package com.benjamin.parsy.ksb.user.infrastructure.kafka;

import com.benjamin.parsy.ksb.user.domain.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.user.domain.model.event.UserValidatedEvent;
import com.benjamin.parsy.ksb.user.domain.publisher.UserEventPublisher;
import com.benjamin.parsy.ksb.user.infrastructure.jpa.persistence.kafkaevent.KafkaEventService;
import com.benjamin.parsy.ksb.user.infrastructure.kafka.event.orderfailedevent.OrderFailedEventAdapter;
import com.benjamin.parsy.ksb.user.infrastructure.kafka.event.orderfailedevent.OrderFailedKafkaEvent;
import com.benjamin.parsy.ksb.user.infrastructure.kafka.event.uservalidated.UserValidatedEventAdapter;
import com.benjamin.parsy.ksb.user.infrastructure.kafka.event.uservalidated.UserValidatedKafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventPublisherKafkaImpl implements UserEventPublisher {

    private final KafkaTemplate<String, UserValidatedKafkaEvent> kafkaTemplateUserEvent;
    private final KafkaTemplate<String, OrderFailedKafkaEvent> kafkaTemplateOrderEvent;
    private final OrderFailedEventAdapter orderFailedEventAdapter;
    private final UserValidatedEventAdapter userValidatedEventAdapter;
    private final KafkaEventService kafkaEventService;

    @Override
    public void publishUserValidated(UserValidatedEvent userValidatedEvent) {

        UserValidatedKafkaEvent userValidatedKafkaEvent = userValidatedEventAdapter.toKafkaEvent(userValidatedEvent);

        kafkaEventService.saveEvent(userValidatedKafkaEvent, KafkaConstant.TOPIC_USER_VALIDATED);
        kafkaTemplateUserEvent.send(KafkaConstant.TOPIC_USER_VALIDATED, userValidatedKafkaEvent);
    }

    @Override
    public void publishOrderFailed(OrderFailedEvent orderFailedEvent) {

        OrderFailedKafkaEvent orderFailedKafkaEvent = orderFailedEventAdapter.toKafkaEvent(orderFailedEvent);

        kafkaEventService.saveEvent(orderFailedKafkaEvent, KafkaConstant.TOPIC_ORDER_FAILED);
        kafkaTemplateOrderEvent.send(KafkaConstant.TOPIC_ORDER_FAILED, orderFailedKafkaEvent);

    }

}
