package com.benjamin.parsy.ksb.order.infrastructure.kafka.event.ordercreatedevent;

import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.OrderKafkaEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class OrderCreatedKafkaEvent extends OrderKafkaEvent {

    @JsonProperty("userUuid")
    private final UUID userUuid;

    @JsonProperty("orderDate")
    private final String orderDate;

    @JsonProperty("status")
    private final String status;

    @JsonProperty("products")
    private final List<Product> products;

    @JsonProperty("totalPrice")
    private final double totalPrice;

    protected OrderCreatedKafkaEvent(UUID orderUuid, UUID userUuid, String orderDate, String status, List<Product> products, double totalPrice) {
        super(orderUuid);
        this.userUuid = userUuid;
        this.orderDate = orderDate;
        this.status = status;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public record Product(@JsonProperty("productUuid") UUID productUuid,
                          @JsonProperty("quantity") int quantity,
                          @JsonProperty("price") double price) {}

}
