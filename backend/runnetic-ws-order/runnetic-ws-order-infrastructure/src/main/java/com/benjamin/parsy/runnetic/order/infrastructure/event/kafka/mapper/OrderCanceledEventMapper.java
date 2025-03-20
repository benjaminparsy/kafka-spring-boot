package com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.runnetic.avro.dto.OrderCanceledKafkaEvent;
import com.benjamin.parsy.runnetic.order.entity.model.event.Event;
import com.benjamin.parsy.runnetic.order.entity.model.event.OrderCanceledEvent;
import com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.KafkaConstant;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Service
public class OrderCanceledEventMapper implements KafkaEventMapper {

    @Override
    public SpecificRecord toKafkaEvent(Event event) {

        if (!(event instanceof OrderCanceledEvent orderCanceledEvent)) {
            throw new IllegalArgumentException("Unable to map business event to kafka event");
        }

        return new OrderCanceledKafkaEvent(
                orderCanceledEvent.getOrderUuid().toString(),
                orderCanceledEvent.getCause()
        );
    }

    @Override
    public String getTopic() {
        return KafkaConstant.Producer.TOPIC_ORDER_CANCELED;
    }

}
