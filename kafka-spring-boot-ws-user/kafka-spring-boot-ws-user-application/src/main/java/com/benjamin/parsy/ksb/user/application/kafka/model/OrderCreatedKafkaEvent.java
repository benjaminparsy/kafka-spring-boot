package com.benjamin.parsy.ksb.user.application.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record OrderCreatedKafkaEvent(@JsonProperty("orderUuid") UUID orderUuid,
                                     @JsonProperty("userUuid") UUID userUuid,
                                     @JsonProperty("orderDate") String orderDate,
                                     @JsonProperty("status") String status,
                                     @JsonProperty("products") List<Product> products,
                                     @JsonProperty("totalPrice") double totalPrice) {

    public record Product(@JsonProperty("productUuid") UUID productUuid,
                          @JsonProperty("quantity") int quantity,
                          @JsonProperty("price") double price) {
    }

}
