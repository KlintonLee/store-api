package com.klinton.store.infrastructure.purchase.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record GetPurchaseResponse(
        @JsonProperty("id") String id,
        @JsonProperty("customer_id") String customerId,
        @JsonProperty("address_id") String addressId,
        @JsonProperty("purchase_date") Instant purchaseDate,
        @JsonProperty("total_price") double totalPrice,
        @JsonProperty("payment_method") String paymentMethod,
        @JsonProperty("status") String status,
        @JsonProperty("updated_at") Instant updatedAt
) {
}
