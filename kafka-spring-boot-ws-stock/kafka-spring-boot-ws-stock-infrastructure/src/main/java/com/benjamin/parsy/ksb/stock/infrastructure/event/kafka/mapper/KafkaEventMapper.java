package com.benjamin.parsy.ksb.stock.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.ksb.stock.entity.model.event.Event;
import org.apache.avro.specific.SpecificRecord;

public interface KafkaEventMapper {

    SpecificRecord toKafkaEvent(Event event);

    String getTopic();

}
