package com.benjamin.parsy.ksb.order.application.kafka.model;

import java.util.UUID;

public record OrderFailedEvent(UUID uuid, String cause) {

}
