package com.benjamin.parsy.ksb.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = KafkaConstant.TOPIC_1, groupId = KafkaConstant.GROUP_ID)
    public void listen(String message) {
        log.info("Received message: " + message);
    }

}
