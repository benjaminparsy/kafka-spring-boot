package com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.port;

import com.benjamin.parsy.runnetic.order.entity.model.event.Event;
import com.benjamin.parsy.runnetic.order.usecase.port.EventPort;
import com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.mapper.KafkaEventMapper;
import com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.mapper.KafkaMapperFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPort implements EventPort {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;
    private final KafkaMapperFactory kafkaMapperFactory;

    @Override
    public void publish(Event event) {

        KafkaEventMapper kafkaEventMapper = kafkaMapperFactory.get(event.getEventType());

        SpecificRecord specificRecord = kafkaEventMapper.toKafkaEvent(event);
        String topic = kafkaEventMapper.getTopic();

        kafkaTemplate.send(topic, specificRecord);

    }

}
