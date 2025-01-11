package com.benjamin.parsy.ksb.order.kafkaevent;

import com.benjamin.parsy.ksb.shared.service.JsonHelper;
import com.benjamin.parsy.ksb.shared.service.MessageService;
import com.benjamin.parsy.ksb.shared.service.impl.GenericServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class KafkaEventServiceImpl extends GenericServiceImpl<KafKaEvent> implements KafkaEventService {

    private final KafkaEventRepository repository;

    public KafkaEventServiceImpl(KafkaEventRepository repository, MessageService messageService) {
        super(repository, messageService);
        this.repository = repository;
    }

    @Override
    public KafKaEvent save(String eventType, Object payload) throws JsonProcessingException {

        String json = JsonHelper.toJson(payload);

        return repository.save(KafKaEvent.builder()
                .eventDate(OffsetDateTime.now())
                .eventType(eventType)
                .payload(json)
                .build());
    }

}
