package com.benjamin.parsy.ksb.user.usecase;

import com.benjamin.parsy.ksb.user.entity.event.UserEventPublisher;
import com.benjamin.parsy.ksb.user.entity.exception.UserNotFoundException;
import com.benjamin.parsy.ksb.user.entity.gateway.EventGateway;
import com.benjamin.parsy.ksb.user.entity.gateway.UserGateway;
import com.benjamin.parsy.ksb.user.entity.model.User;
import com.benjamin.parsy.ksb.user.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.user.entity.model.event.UserValidatedEvent;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ValidatedUserUseCase {

    private final UserGateway userGateway;
    private final EventGateway eventGateway;
    private final UserEventPublisher userEventPublisher;

    /**
     * To validate a user for an order
     *
     * @param orderUuid Order identifier
     * @param userUuid User identifier
     */
    public void validateUser(UUID orderUuid, UUID userUuid) {

        try {

            User user = userGateway.findById(userUuid);

            UserValidatedEvent userValidatedEvent = new UserValidatedEvent(orderUuid);
            eventGateway.save(userValidatedEvent);
            userEventPublisher.publishUserValidated(userValidatedEvent);

        } catch (UserNotFoundException e) {

            OrderFailedEvent orderFailedEvent = new OrderFailedEvent(orderUuid, e.getMessage());
            eventGateway.save(orderFailedEvent);
            userEventPublisher.publishOrderFailed(orderFailedEvent);

        }

    }

}
