package com.klinton.store.infrastructure.purchase.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klinton.store.domain.core.customer.Customer;

public record GetPurchaseCustomerResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("phone") String phone
) {

    public static GetPurchaseCustomerResponse from(final Customer customer) {
        if (customer == null) {
            return null;
        }

        return new GetPurchaseCustomerResponse(
                customer.getId().getValue(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }
}
