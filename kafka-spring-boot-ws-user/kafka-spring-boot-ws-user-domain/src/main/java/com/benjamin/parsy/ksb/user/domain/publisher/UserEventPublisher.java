package com.benjamin.parsy.ksb.user.domain.publisher;

import com.benjamin.parsy.ksb.user.domain.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.user.domain.model.event.UserValidatedEvent;

public interface UserEventPublisher {

    void publishUserValidated(UserValidatedEvent userValidatedEvent);

    void publishOrderFailed(OrderFailedEvent orderFailedEvent);

}
