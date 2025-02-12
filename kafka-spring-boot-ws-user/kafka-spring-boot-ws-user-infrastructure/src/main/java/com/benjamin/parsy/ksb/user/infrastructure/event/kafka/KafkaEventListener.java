package com.benjamin.parsy.ksb.user.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.user.entity.event.EventListener;
import com.benjamin.parsy.ksb.user.entity.model.event.OrderCreatedEvent;
import com.benjamin.parsy.ksb.user.infrastructure.configuration.KafkaConstant;
import com.benjamin.parsy.ksb.user.usecase.ValidatedUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventListener implements EventListener {

    private final ValidatedUserUseCase validatedUserUseCase;

    @Override
    @KafkaListener(topics = KafkaConstant.TOPIC_ORDER_CREATED, groupId = KafkaConstant.GROUP_ID_USER)
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        validatedUserUseCase.validateUser(orderCreatedEvent.getOrderUuid(), orderCreatedEvent.getUserUuid());
    }

}
