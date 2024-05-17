package com.klinton.store.infrastructure.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateProductDto(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("quantity") int quantity,
        @JsonProperty("price") double price
) {
}
