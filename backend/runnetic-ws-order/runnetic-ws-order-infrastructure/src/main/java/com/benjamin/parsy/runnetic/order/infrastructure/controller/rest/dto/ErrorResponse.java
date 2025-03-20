package com.benjamin.parsy.runnetic.order.infrastructure.controller.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorResponse {

    public ErrorResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public ErrorResponse(String code, String description, Map<String, Object> extensions) {
        this.code = code;
        this.description = description;
        this.extensions = extensions;
    }

    @JsonProperty
    private String code;

    @JsonProperty
    private String description;

    @JsonProperty
    private Map<String, Object> extensions;

}
