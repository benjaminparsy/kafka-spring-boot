package com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.runnetic.avro.dto.DesiredProductKafkaEvent;
import com.benjamin.parsy.runnetic.avro.dto.OrderCreatedKafkaEvent;
import com.benjamin.parsy.runnetic.order.entity.model.event.Event;
import com.benjamin.parsy.runnetic.order.entity.model.event.OrderCreatedEvent;
import com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.KafkaConstant;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderCreatedEventMapper implements KafkaEventMapper {

    @Override
    public SpecificRecord toKafkaEvent(Event event) {

        if (!(event instanceof OrderCreatedEvent orderCreatedEvent)) {
            throw new IllegalArgumentException("Unable to map business event to kafka event");
        }

        List<DesiredProductKafkaEvent> desiredProductKafkaEventList = orderCreatedEvent.getProducts()
                .stream()
                .map(dp -> new DesiredProductKafkaEvent(
                        dp.getProductUuid().toString(),
                        dp.getQuantity(),
                        dp.getPrice()))
                .toList();

        return new OrderCreatedKafkaEvent(
                orderCreatedEvent.getOrderUuid().toString(),
                orderCreatedEvent.getUserUuid().toString(),
                orderCreatedEvent.getOrderDate().toString(),
                orderCreatedEvent.getStatus().toString(),
                desiredProductKafkaEventList,
                orderCreatedEvent.getTotalPrice()
        );

    }

    @Override
    public String getTopic() {
        return KafkaConstant.Producer.TOPIC_ORDER_CONFIRMED;
    }

}
