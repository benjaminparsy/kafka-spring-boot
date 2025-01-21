package com.benjamin.parsy.ksb.order.application.kafka;

import com.benjamin.parsy.ksb.order.application.kafka.model.OrderFailedEvent;
import com.benjamin.parsy.ksb.order.domain.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderKafkaConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = KafkaConstant.TOPIC_ORDER_FAILED, groupId = KafkaConstant.GROUP_ID_ORDER)
    public void handleOrderFailed(OrderFailedEvent event) throws OrderNotFoundException {
        orderService.cancelOrder(event.uuid(), event.cause());
    }

}
