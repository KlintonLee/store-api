package com.klinton.store.infrastructure.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateAdminDto(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("active") Boolean active
) {
}
