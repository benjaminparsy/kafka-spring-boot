package com.benjamin.parsy.ksb.user.domain.service;

import com.benjamin.parsy.ksb.user.domain.model.User;
import com.benjamin.parsy.ksb.user.domain.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.user.domain.model.event.UserValidatedEvent;
import com.benjamin.parsy.ksb.user.domain.publisher.UserEventPublisher;
import com.benjamin.parsy.ksb.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserEventPublisher userEventPublisher;

    /**
     * To validate a user for an order
     *
     * @param orderUuid Order identifier
     * @param userUuid User identifier
     */
    public void validateUser(UUID orderUuid, UUID userUuid) {

        Optional<User> optionalUser = userRepository.findById(userUuid);

        if (optionalUser.isEmpty()) {
            OrderFailedEvent orderFailedEvent = new OrderFailedEvent(orderUuid, "User not found for id " + userUuid);
            userEventPublisher.publishOrderFailed(orderFailedEvent);
        } else {
            UserValidatedEvent userValidatedEvent = new UserValidatedEvent(orderUuid);
            userEventPublisher.publishUserValidated(userValidatedEvent);
        }

    }

}
