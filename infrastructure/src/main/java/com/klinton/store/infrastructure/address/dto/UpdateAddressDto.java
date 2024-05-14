package com.klinton.store.infrastructure.address.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klinton.store.domain.core.address.States;

public record UpdateAddressDto(
        @JsonProperty("customer_id") String customerId,
        @JsonProperty("street") String street,
        @JsonProperty("city") String city,
        @JsonProperty("neighborhood") String neighborhood,
        @JsonProperty("state") States state,
        @JsonProperty("number") String number,
        @JsonProperty("zip_code") String zipCode
) {
}
