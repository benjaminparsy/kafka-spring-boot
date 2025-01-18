package com.benjamin.parsy.ksb.order.stockprojection.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockProjectionEvent {

    @Min(1)
    @JsonProperty("productId")
    private Long productId;

    @NotBlank
    @Column(name = "productName")
    private String productName;

    @PositiveOrZero
    @Column(name = "price")
    private int price;

    @PositiveOrZero
    @Column(name = "stockQuantity")
    private int stockQuantity;

}
