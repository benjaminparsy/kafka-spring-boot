package com.benjamin.parsy.ksb.shared.kafka;

import com.benjamin.parsy.ksb.shared.service.jpa.GenericService;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaEventService extends GenericService<KafKaEvent> {

    KafKaEvent save(String eventType, Object payload) throws JsonProcessingException;

}
