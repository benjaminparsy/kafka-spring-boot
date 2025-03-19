package com.benjamin.parsy.ksb.order.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.ksb.order.entity.model.event.Event;
import org.apache.avro.specific.SpecificRecord;

public interface KafkaEventMapper {

    SpecificRecord toKafkaEvent(Event event);

    String getTopic();

}
