package com.benjamin.parsy.runnetic.user.infrastructure.event.kafka;

import com.benjamin.parsy.runnetic.avro.dto.OrderCreatedKafkaEvent;
import com.benjamin.parsy.runnetic.user.infrastructure.configuration.KafkaConstant;
import com.benjamin.parsy.runnetic.user.usecase.ValidatedUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaEventListener {

    private final ValidatedUserUseCase validatedUserUseCase;

    @KafkaListener(topics = KafkaConstant.Consumer.TOPIC_ORDER_CREATED, groupId = KafkaConstant.GROUP_ID_USER)
    public void handleOrderCreatedEvent(OrderCreatedKafkaEvent orderCreatedKafkaEvent) {

        UUID orderUuid = UUID.fromString(orderCreatedKafkaEvent.getOrderUuid());
        UUID userUuid = UUID.fromString(orderCreatedKafkaEvent.getUserUuid());

        validatedUserUseCase.validateUser(orderUuid, userUuid);

    }

}
