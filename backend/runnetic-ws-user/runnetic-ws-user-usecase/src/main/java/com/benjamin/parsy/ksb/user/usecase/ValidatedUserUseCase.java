package com.benjamin.parsy.runnetic.user.usecase;

import com.benjamin.parsy.runnetic.user.entity.exception.UserNotFoundException;
import com.benjamin.parsy.runnetic.user.entity.gateway.EventGateway;
import com.benjamin.parsy.runnetic.user.entity.gateway.UserGateway;
import com.benjamin.parsy.runnetic.user.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.runnetic.user.entity.model.event.UserValidatedEvent;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ValidatedUserUseCase {

    private final UserGateway userGateway;
    private final EventGateway eventGateway;

    /**
     * To validate a user for an order
     *
     * @param orderUuid Order identifier
     * @param userUuid User identifier
     */
    public void validateUser(UUID orderUuid, UUID userUuid) {

        try {

            userGateway.existsById(userUuid);

            UserValidatedEvent userValidatedEvent = new UserValidatedEvent(orderUuid);
            eventGateway.publish(userValidatedEvent);

        } catch (UserNotFoundException e) {

            OrderFailedEvent orderFailedEvent = new OrderFailedEvent(orderUuid, e.getMessage());
            eventGateway.publish(orderFailedEvent);

        }

    }

}
