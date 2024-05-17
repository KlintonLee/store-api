package com.klinton.store.infrastructure.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateProductDto(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("quantity") int quantity,
        @JsonProperty("price") double price,
        @JsonProperty("active") boolean active
) {
}
