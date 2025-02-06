package com.benjamin.parsy.ksb.order.application.rest.handler;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FieldErrorRecord(@JsonProperty("field") String field, @JsonProperty("message") String message) {
}
