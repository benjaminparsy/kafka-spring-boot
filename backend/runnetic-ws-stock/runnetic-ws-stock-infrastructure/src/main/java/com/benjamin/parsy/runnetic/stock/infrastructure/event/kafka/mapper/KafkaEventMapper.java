package com.benjamin.parsy.runnetic.stock.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.runnetic.stock.entity.model.event.Event;
import org.apache.avro.specific.SpecificRecord;

public interface KafkaEventMapper {

    SpecificRecord toKafkaEvent(Event event);

    String getTopic();

}
