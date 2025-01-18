package com.benjamin.parsy.ksb.order.userprojection.kafka;

import com.benjamin.parsy.ksb.order.shared.KafkaConstant;
import com.benjamin.parsy.ksb.order.shared.exception.UserProjectionNotFoundException;
import com.benjamin.parsy.ksb.order.userprojection.UserProjection;
import com.benjamin.parsy.ksb.order.userprojection.UserProjectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProjectionKafkaConsumer {

    private final UserProjectionService userProjectionService;

    @KafkaListener(topics = KafkaConstant.TOPIC_USER_CREATED, groupId = KafkaConstant.GROUP_ID_USER)
    public void handleUserCreated(@Payload @Valid UserProjectionEvent userCreated) {

        userProjectionService.save(UserProjection.builder()
                .name(userCreated.getName())
                .email(userCreated.getEmail())
                .address(userCreated.getAddress())
                .phone(userCreated.getPhone())
                .build());

        log.debug("A user has been created : {}", userCreated);
    }

    @KafkaListener(topics = KafkaConstant.TOPIC_USER_UPDATED, groupId = KafkaConstant.GROUP_ID_USER)
    public void handleUserUpdated(@Payload @Valid UserProjectionEvent userUpdated) throws UserProjectionNotFoundException {

        userProjectionService.updateUser(userUpdated.getUserId(), userUpdated.getName(),
                userUpdated.getEmail(), userUpdated.getAddress(), userUpdated.getPhone());

        log.debug("A user has been updated : {}", userUpdated);
    }

}
