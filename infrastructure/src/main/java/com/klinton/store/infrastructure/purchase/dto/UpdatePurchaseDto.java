package com.klinton.store.infrastructure.purchase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePurchaseDto(
        @JsonProperty("address_id") String addressId,
        @JsonProperty("total_price") double totalPrice,
        @JsonProperty("payment_method") String paymentMethod,
        @JsonProperty("status") String status
) {
}
