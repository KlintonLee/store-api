package com.klinton.store.infrastructure.address.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klinton.store.domain.core.address.States;

public record GetAddressResponse(
        @JsonProperty("id") String id,
        @JsonProperty("customer_id") String customerId,
        @JsonProperty("street") String street,
        @JsonProperty("number") String number,
        @JsonProperty("neighborhood") String neighborhood,
        @JsonProperty("city") String city,
        @JsonProperty("state") States state,
        @JsonProperty("zip_code") String zipCode
) {
}
