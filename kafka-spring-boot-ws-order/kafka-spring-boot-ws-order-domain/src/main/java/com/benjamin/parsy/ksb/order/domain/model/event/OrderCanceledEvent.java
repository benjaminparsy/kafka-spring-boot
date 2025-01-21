package com.benjamin.parsy.ksb.order.domain.model.event;

import java.util.UUID;

public record OrderCanceledEvent(UUID uuid, String cause) {
}
