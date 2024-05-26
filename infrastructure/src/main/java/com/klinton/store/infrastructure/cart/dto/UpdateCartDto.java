package com.klinton.store.infrastructure.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateCartDto(
        @JsonProperty("id") String id,
        @JsonProperty("quantity") int quantity,
        @JsonProperty("price") double price
) {
}
