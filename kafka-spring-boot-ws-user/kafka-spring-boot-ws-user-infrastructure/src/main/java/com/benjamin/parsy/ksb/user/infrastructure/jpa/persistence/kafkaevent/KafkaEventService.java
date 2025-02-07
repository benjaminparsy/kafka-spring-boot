package com.benjamin.parsy.ksb.user.infrastructure.jpa.persistence.kafkaevent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private final KafkaEventRepository kafkaEventRepository;

    public void saveEvent(Object event, String eventType) {

        try {

            String jsonPayload = OBJECT_MAPPER.writeValueAsString(event);

            KafKaEventEntity kafKaEventEntity = KafKaEventEntity.builder()
                    .eventDate(OffsetDateTime.now())
                    .eventType(eventType)
                    .payload(jsonPayload)
                    .build();

            kafkaEventRepository.save(kafKaEventEntity);

        } catch (JsonProcessingException e) {
            log.error("An error has occurred while recording a kafka event");
        }

    }

}
