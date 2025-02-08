package com.benjamin.parsy.ksb.user.infrastructure.kafka.event.orderfailedevent;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record OrderFailedKafkaEvent(@JsonProperty("orderUuid") UUID orderUuid,
                                    @JsonProperty("cause") String cause) {
}
