package com.benjamin.parsy.ksb.user.infrastructure.kafka.event.uservalidated;

import com.benjamin.parsy.ksb.user.domain.model.event.UserValidatedEvent;
import com.benjamin.parsy.ksb.user.infrastructure.kafka.event.EventKafkaAdapter;
import org.springframework.stereotype.Component;

@Component
public class UserValidatedEventAdapter implements EventKafkaAdapter<UserValidatedEvent, UserValidatedKafkaEvent> {

    @Override
    public UserValidatedKafkaEvent toKafkaEvent(UserValidatedEvent modelEvent) {
        return new UserValidatedKafkaEvent(
                modelEvent.orderUuid()
        );
    }

}
