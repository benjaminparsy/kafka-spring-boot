package com.benjamin.parsy.ksb.user.infrastructure.db.gateway;

import com.benjamin.parsy.ksb.user.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.user.entity.model.event.Event;
import com.benjamin.parsy.ksb.user.infrastructure.db.jpa.repository.KafkaEventRepository;
import com.benjamin.parsy.ksb.user.infrastructure.db.jpa.schema.KafKaEventEntity;
import com.benjamin.parsy.ksb.user.infrastructure.shared.JsonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaEventGateway implements EventGateway {

    private final KafkaEventRepository kafkaEventRepository;

    @Override
    public void save(Event event) {

        try {

            String jsonPayload = JsonHelper.toJson(event);

            KafKaEventEntity kafKaEventEntity = KafKaEventEntity.builder()
                    .eventDate(OffsetDateTime.now())
                    .eventType(event.getEventType().name())
                    .payload(jsonPayload)
                    .build();

            kafkaEventRepository.save(kafKaEventEntity);

        } catch (JsonProcessingException e) {
            log.error("An error has occurred while recording a kafka event");
        }

    }

}
