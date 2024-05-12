package com.klinton.store.infrastructure.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCustomerDto(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("phone") String phone
) {
}
