package com.benjamin.parsy.ksb.user.infrastructure.kafka.event.uservalidated;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserValidatedKafkaEvent(@JsonProperty("orderUuid") UUID orderUuid) {
}
