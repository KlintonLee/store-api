package com.klinton.store.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateAdminDto(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
) {
}
