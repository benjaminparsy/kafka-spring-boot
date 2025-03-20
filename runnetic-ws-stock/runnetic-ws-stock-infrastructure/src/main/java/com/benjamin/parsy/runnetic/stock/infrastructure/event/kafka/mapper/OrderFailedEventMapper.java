package com.benjamin.parsy.runnetic.stock.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.runnetic.avro.dto.OrderFailedKafkaEvent;
import com.benjamin.parsy.runnetic.stock.entity.model.event.Event;
import com.benjamin.parsy.runnetic.stock.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.runnetic.stock.infrastructure.configuration.KafkaConstant;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Service
public class OrderFailedEventMapper implements KafkaEventMapper {

    @Override
    public SpecificRecord toKafkaEvent(Event event) {

        if (!(event instanceof OrderFailedEvent orderFailedEvent)) {
            throw new IllegalArgumentException("Unable to map business event to kafka event");
        }

        return new OrderFailedKafkaEvent(
                orderFailedEvent.getOrderUuid().toString(),
                orderFailedEvent.getCause()
        );
    }

    @Override
    public String getTopic() {
        return KafkaConstant.Producer.TOPIC_ORDER_FAILED;
    }

}
