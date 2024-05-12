package com.klinton.store.infrastructure.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateCustomerDto(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("phone") String phone,
        @JsonProperty("active") Boolean active
) {
}
