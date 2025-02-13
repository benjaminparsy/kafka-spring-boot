package com.benjamin.parsy.ksb.user.entity.event;

import com.benjamin.parsy.ksb.user.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.user.entity.model.event.UserValidatedEvent;

public interface EventPublisher {

    void publishOrderFailedEvent(OrderFailedEvent orderFailedEvent);

    void publishUserValidatedEvent(UserValidatedEvent userValidatedEvent);

}
