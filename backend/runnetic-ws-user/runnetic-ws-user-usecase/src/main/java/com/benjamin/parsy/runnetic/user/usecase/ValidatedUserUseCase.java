package com.benjamin.parsy.runnetic.user.usecase;

import com.benjamin.parsy.runnetic.user.usecase.exception.UserNotFoundException;
import com.benjamin.parsy.runnetic.user.usecase.port.EventPort;
import com.benjamin.parsy.runnetic.user.usecase.port.UserPort;
import com.benjamin.parsy.runnetic.user.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.runnetic.user.entity.model.event.UserValidatedEvent;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ValidatedUserUseCase {

    private final UserPort userPort;
    private final EventPort eventPort;

    /**
     * To validate a user for an order
     *
     * @param orderUuid Order identifier
     * @param userUuid User identifier
     */
    public void validateUser(UUID orderUuid, UUID userUuid) {

        try {

            userPort.existsById(userUuid);

            UserValidatedEvent userValidatedEvent = new UserValidatedEvent(orderUuid);
            eventPort.publish(userValidatedEvent);

        } catch (UserNotFoundException e) {

            OrderFailedEvent orderFailedEvent = new OrderFailedEvent(orderUuid, e.getMessage());
            eventPort.publish(orderFailedEvent);

        }

    }

}
