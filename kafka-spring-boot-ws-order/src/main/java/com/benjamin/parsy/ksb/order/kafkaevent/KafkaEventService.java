package com.benjamin.parsy.ksb.order.kafkaevent;

import com.benjamin.parsy.ksb.shared.service.GenericService;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaEventService extends GenericService<KafKaEvent> {

    KafKaEvent save(String eventType, Object payload) throws JsonProcessingException;

}
