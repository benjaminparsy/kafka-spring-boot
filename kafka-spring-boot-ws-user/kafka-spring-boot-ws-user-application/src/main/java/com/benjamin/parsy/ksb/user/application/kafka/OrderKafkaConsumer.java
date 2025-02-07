package com.benjamin.parsy.ksb.user.application.kafka;

import com.benjamin.parsy.ksb.user.domain.service.UserService;
import com.benjamin.parsy.ksb.user.application.kafka.model.OrderCreatedKafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderKafkaConsumer {

    private final UserService userService;

    @KafkaListener(topics = KafkaConstant.TOPIC_ORDER_CREATED, groupId = KafkaConstant.GROUP_ID_USER)
    public void handleOrderCreated(OrderCreatedKafkaEvent event) {
        userService.validateUser(event.orderUuid(), event.userUuid());
    }

}
