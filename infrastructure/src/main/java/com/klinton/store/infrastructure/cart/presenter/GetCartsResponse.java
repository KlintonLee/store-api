package com.klinton.store.infrastructure.cart.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetCartsResponse(
        @JsonProperty("id") String id,
        @JsonProperty("product_id") String productId,
        @JsonProperty("quantity") int quantity,
        @JsonProperty("price") double price
) {
}
