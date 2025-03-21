package com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.runnetic.order.entity.model.event.EventType;
import com.benjamin.parsy.runnetic.order.infrastructure.utils.ClassNameUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaMapperFactory {

    private final Map<EventType, KafkaEventMapper> kafkaEventMappers;

    public KafkaMapperFactory(Map<String, KafkaEventMapper> kafkaEventMappers) {
        this.kafkaEventMappers = Map.of(
                EventType.ORDER_CANCELED, kafkaEventMappers.get(ClassNameUtils.getClassNameWithFirstLetterLowerCase(OrderCanceledEventMapper.class)),
                EventType.ORDER_CREATED, kafkaEventMappers.get(ClassNameUtils.getClassNameWithFirstLetterLowerCase(OrderCreatedEventMapper.class)),
                EventType.ORDER_CONFIRMED, kafkaEventMappers.get(ClassNameUtils.getClassNameWithFirstLetterLowerCase(OrderConfirmedEventMapper.class))
        );
    }

    public KafkaEventMapper get(EventType eventType) {
        return kafkaEventMappers.get(eventType);
    }

}
