package com.benjamin.parsy.ksb.user.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.ksb.user.entity.model.event.EventType;
import com.benjamin.parsy.ksb.user.infrastructure.shared.ClassNameUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaMapperFactory {

    private final Map<EventType, KafkaEventMapper> kafkaEventMappers;

    public KafkaMapperFactory(Map<String, KafkaEventMapper> kafkaEventMappers) {
        this.kafkaEventMappers = Map.of(
                EventType.ORDER_FAILED, kafkaEventMappers.get(ClassNameUtils.getClassNameWithFirstLetterLowerCase(OrderFailedEventMapper.class)),
                EventType.USER_VALIDATED, kafkaEventMappers.get(ClassNameUtils.getClassNameWithFirstLetterLowerCase(UserValidatedEventMapper.class))
        );
    }

    public KafkaEventMapper get(EventType eventType) {
        return kafkaEventMappers.get(eventType);
    }

}
