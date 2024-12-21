package com.benjamin.parsy.ksb.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    public ErrorResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonProperty
    private String code;

    @JsonProperty
    private String description;

    @JsonProperty
    private Map<String, Object> extensions;

}
