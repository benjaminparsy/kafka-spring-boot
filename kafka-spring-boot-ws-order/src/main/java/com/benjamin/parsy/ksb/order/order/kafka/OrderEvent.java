package com.benjamin.parsy.ksb.order.order.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class OrderEvent {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("userId")
    private Long userProjectionId;

    @JsonProperty("products")
    private List<Product> productList;

    @JsonProperty("orderDate")
    private OffsetDateTime orderDate;

    @JsonProperty("orderStatus")
    private String orderStatus;

    @JsonProperty("orderTotal")
    private double orderTotal;

    public record Product(@JsonProperty("productId") Long productId, @JsonProperty("orderTotal") int quantity) {}

}
