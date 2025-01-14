package com.benjamin.parsy.ksb.shared.kafka;

import com.benjamin.parsy.ksb.shared.exception.AbstractMessageException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class KafkaProducerGeneric<I, O> {

    private final KafkaTemplate<String, O> kafkaTemplate;
    private final KafkaEventService kafkaEventService;

    @Transactional
    public void send(String topic, I entity) throws AbstractMessageException, JsonProcessingException {

        validateEntity(entity);

        O kafkaObject = toKafkaObject(entity);

        kafkaEventService.save(topic, kafkaObject);
        kafkaTemplate.send(topic, kafkaObject);

    }

    /**
     * Validates OrderProduct list before processing.
     */
    protected abstract void validateEntity(I orderProductList) throws AbstractMessageException;

    protected abstract O toKafkaObject(I entity);

}
