package com.benjamin.parsy.runnetic.user.entity.model.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserValidatedEvent extends Event {

    private final UUID orderUuid;

    public UserValidatedEvent(UUID orderUuid) {
        super(EventType.USER_VALIDATED);
        this.orderUuid = orderUuid;
    }

}
