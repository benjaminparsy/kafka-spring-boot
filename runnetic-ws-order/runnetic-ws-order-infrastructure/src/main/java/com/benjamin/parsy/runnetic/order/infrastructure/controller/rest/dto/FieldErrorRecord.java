package com.benjamin.parsy.runnetic.order.infrastructure.controller.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FieldErrorRecord(@JsonProperty("field") String field,
                               @JsonProperty("message") String message) {
}
