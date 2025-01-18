package com.benjamin.parsy.ksb.order.userprojection.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProjectionEvent {

    @Min(1)
    @JsonProperty("userId")
    private Long userId;

    @NotBlank
    @Size(max = 255)
    @JsonProperty("name")
    private String name;

    @NotBlank
    @Email
    @Size(max = 255)
    @JsonProperty("email")
    private String email;

    @Size(max = 255)
    @JsonProperty("address")
    private String address;

    @Size(max = 20)
    @JsonProperty("phone")
    private String phone;

}
