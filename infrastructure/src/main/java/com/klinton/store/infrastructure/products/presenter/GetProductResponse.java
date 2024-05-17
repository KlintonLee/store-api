package com.klinton.store.infrastructure.products.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record GetProductResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("quantity") int quantity,
        @JsonProperty("price") double price,
        @JsonProperty("active") boolean active,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt,
        @JsonProperty("deleted_at") Instant deletedAt
) {
}
