package com.benjamin.parsy.ksb.order.userprojection.kafka;

import com.benjamin.parsy.ksb.order.shared.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerUserService {

    @KafkaListener(topics = KafkaConstant.TOPIC_USER_CREATED, groupId = KafkaConstant.GROUP_ID_USER)
    public void handleUserCreated(String userCreated) {
        log.info("A user has been created : {}", userCreated);
    }

    @KafkaListener(topics = KafkaConstant.TOPIC_USER_UPDATED, groupId = KafkaConstant.GROUP_ID_USER)
    public void handleUserUpdated(String userUpdated) {
        log.info("A user has been updated : {}", userUpdated);
    }

}
