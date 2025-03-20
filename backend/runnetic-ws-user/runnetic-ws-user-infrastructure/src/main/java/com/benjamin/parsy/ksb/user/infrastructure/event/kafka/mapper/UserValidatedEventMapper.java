package com.benjamin.parsy.runnetic.user.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.runnetic.avro.dto.UserValidatedKafkaEvent;
import com.benjamin.parsy.runnetic.user.entity.model.event.Event;
import com.benjamin.parsy.runnetic.user.entity.model.event.UserValidatedEvent;
import com.benjamin.parsy.runnetic.user.infrastructure.configuration.KafkaConstant;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Service
public class UserValidatedEventMapper implements KafkaEventMapper {

    @Override
    public SpecificRecord toKafkaEvent(Event event) {

        if (!(event instanceof UserValidatedEvent userValidatedEvent)) {
            throw new IllegalArgumentException("Unable to map business event to kafka event");
        }

        return new UserValidatedKafkaEvent(
                userValidatedEvent.getOrderUuid().toString()
        );
    }

    @Override
    public String getTopic() {
        return KafkaConstant.Producer.TOPIC_USER_VALIDATED;
    }

}
