package com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.runnetic.order.entity.model.event.Event;
import org.apache.avro.specific.SpecificRecord;

public interface KafkaEventMapper {

    SpecificRecord toKafkaEvent(Event event);

    String getTopic();

}
