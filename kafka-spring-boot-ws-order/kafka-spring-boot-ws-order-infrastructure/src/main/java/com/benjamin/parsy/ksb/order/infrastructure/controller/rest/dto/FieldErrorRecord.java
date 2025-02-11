package com.benjamin.parsy.ksb.order.infrastructure.controller.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FieldErrorRecord(@JsonProperty("field") String field,
                               @JsonProperty("message") String message) {
}
