package com.klinton.store.infrastructure.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCartDto(
        @JsonProperty("customerId") String customerId,
        @JsonProperty("productId") String productId,
        @JsonProperty("quantity") int quantity,
        @JsonProperty("price") double price
) {
}
