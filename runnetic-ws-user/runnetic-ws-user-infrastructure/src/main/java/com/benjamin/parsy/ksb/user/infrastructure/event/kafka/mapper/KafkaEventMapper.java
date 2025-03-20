package com.benjamin.parsy.runnetic.user.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.runnetic.user.entity.model.event.Event;
import org.apache.avro.specific.SpecificRecord;

public interface KafkaEventMapper {

    SpecificRecord toKafkaEvent(Event event);

    String getTopic();

}
