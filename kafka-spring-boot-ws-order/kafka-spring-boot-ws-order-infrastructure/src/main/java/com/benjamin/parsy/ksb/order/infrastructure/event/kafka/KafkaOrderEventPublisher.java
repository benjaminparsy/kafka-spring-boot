package com.benjamin.parsy.ksb.order.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.order.entity.event.OrderEventPublisher;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderEvent;
import com.benjamin.parsy.ksb.order.infrastructure.configuration.KafkaConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Override
    public void publish(OrderEvent event) {
        kafkaTemplate.send(KafkaConstant.TOPIC_ORDER_CONFIRMED, event);
    }

}
