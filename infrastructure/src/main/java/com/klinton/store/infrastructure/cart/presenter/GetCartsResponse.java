package com.klinton.store.infrastructure.cart.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klinton.store.application.cart.retrieve.ListCartByCustomerOutput;

public record GetCartsResponse(
        @JsonProperty("id") String id,
        @JsonProperty("product") String product,
        @JsonProperty("quantity") int quantity,
        @JsonProperty("price") double price
) {
    public static GetCartsResponse of(ListCartByCustomerOutput output) {
        return new GetCartsResponse(output.cartId(), output.product().getId().getValue(), output.quantity(), output.price());
    }
}
