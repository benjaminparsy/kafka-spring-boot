package com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.gateway;

import com.benjamin.parsy.runnetic.order.entity.gateway.EventGateway;
import com.benjamin.parsy.runnetic.order.entity.model.event.OrderEvent;
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
public class KafkaEventGateway implements EventGateway {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;
    private final KafkaMapperFactory kafkaMapperFactory;

    @Override
    public void publish(OrderEvent orderEvent) {

        KafkaEventMapper kafkaEventMapper = kafkaMapperFactory.get(orderEvent.getEventType());

        SpecificRecord specificRecord = kafkaEventMapper.toKafkaEvent(orderEvent);
        String topic = kafkaEventMapper.getTopic();

        kafkaTemplate.send(topic, specificRecord);

    }

}
