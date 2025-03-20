package com.benjamin.parsy.runnetic.stock.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.runnetic.stock.entity.model.event.EventType;
import com.benjamin.parsy.runnetic.stock.infrastructure.shared.ClassNameUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaMapperFactory {

    private final Map<EventType, KafkaEventMapper> kafkaEventMappers;

    public KafkaMapperFactory(Map<String, KafkaEventMapper> kafkaEventMappers) {
        this.kafkaEventMappers = Map.of(
                EventType.ORDER_FAILED, kafkaEventMappers.get(ClassNameUtils.getClassNameWithFirstLetterLowerCase(OrderFailedEventMapper.class)),
                EventType.STOCK_RESERVED, kafkaEventMappers.get(ClassNameUtils.getClassNameWithFirstLetterLowerCase(StockReservedEventMapper.class))
        );
    }

    public KafkaEventMapper get(EventType eventType) {
        return kafkaEventMappers.get(eventType);
    }

}
