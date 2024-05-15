package com.klinton.store.infrastructure.customer.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public record GetCustomerResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("phone") String phone,
        @JsonProperty("active") Boolean active,
        @JsonProperty("addresses") List<CustomerAddressListResponse> addresses,
        @JsonProperty("created_at") Instant created_at,
        @JsonProperty("updated_at") Instant updated_at,
        @JsonProperty("deleted_at") Instant deleted_at
) {
}
