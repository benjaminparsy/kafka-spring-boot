package com.benjamin.parsy.runnetic.stock.infrastructure.event.kafka.mapper;

import com.benjamin.parsy.runnetic.stock.entity.model.event.Event;
import com.benjamin.parsy.runnetic.stock.entity.model.event.StockReservedEvent;
import com.benjamin.parsy.runnetic.stock.infrastructure.configuration.KafkaConstant;
import com.benjamin.parsy.runnetic.stock.infrastructure.event.kafka.dto.StockReservedKafkaEvent;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Service
public class StockReservedEventMapper implements KafkaEventMapper {

    @Override
    public SpecificRecord toKafkaEvent(Event event) {

        if (!(event instanceof StockReservedEvent stockReservedEvent)) {
            throw new IllegalArgumentException("Unable to map business event to kafka event");
        }

        return new StockReservedKafkaEvent(
                stockReservedEvent.getOrderUuid().toString()
        );
    }

    @Override
    public String getTopic() {
        return KafkaConstant.Producer.TOPIC_STOCK_RESERVED;
    }

}
