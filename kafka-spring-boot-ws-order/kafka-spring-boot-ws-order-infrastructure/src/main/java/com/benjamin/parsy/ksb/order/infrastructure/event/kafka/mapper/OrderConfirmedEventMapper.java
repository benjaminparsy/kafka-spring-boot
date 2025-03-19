package com.benjamin.parsy.ksb.order.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.ksb.avro.dto.OrderConfirmedKafkaEvent;
import com.benjamin.parsy.ksb.order.entity.model.event.Event;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderConfirmedEvent;
import com.benjamin.parsy.ksb.order.infrastructure.event.kafka.KafkaConstant;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Service
public class OrderConfirmedEventMapper implements KafkaEventMapper {

    @Override
    public SpecificRecord toKafkaEvent(Event event) {

        if (!(event instanceof OrderConfirmedEvent orderConfirmedEvent)) {
            throw new IllegalArgumentException("Unable to map business event to kafka event");
        }

        return new OrderConfirmedKafkaEvent(
                orderConfirmedEvent.getOrderUuid().toString()
        );
    }

    @Override
    public String getTopic() {
        return KafkaConstant.Producer.TOPIC_ORDER_CONFIRMED;
    }

}
