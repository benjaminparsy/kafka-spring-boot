package com.benjamin.parsy.ksb.user.domain.model.event;

import java.util.UUID;

public record UserValidatedEvent(UUID orderUuid) {
}
