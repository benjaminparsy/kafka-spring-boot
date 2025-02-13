package com.benjamin.parsy.ksb.user.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.user.entity.event.EventListener;
import com.benjamin.parsy.ksb.user.infrastructure.configuration.KafkaConstant;
import com.benjamin.parsy.ksb.user.usecase.ValidatedUserUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaEventListener implements EventListener<GenericRecord> {

    private final ValidatedUserUseCase validatedUserUseCase;

    @Override
    @KafkaListener(topics = KafkaConstant.TOPIC_ORDER_CREATED, groupId = KafkaConstant.GROUP_ID_USER)
    public void handleOrderCreatedEvent(GenericRecord orderCreatedEvent) {

        UUID orderUuid = UUID.fromString(orderCreatedEvent.get("orderUuid").toString());
        UUID userUuid = UUID.fromString(orderCreatedEvent.get("userUuid").toString());

        validatedUserUseCase.validateUser(orderUuid, userUuid);

    }

}
