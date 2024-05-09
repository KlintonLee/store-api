package com.klinton.store.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klinton.store.application.admin.retrieve.get.GetAdminByIdOutput;

import java.time.Instant;

public record GetAdminOutput(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("active") Boolean active,
        @JsonProperty("created_at") Instant created_at,
        @JsonProperty("updated_at") Instant updated_at,
        @JsonProperty("deleted_at") Instant deleted_at
) {

    public static GetAdminOutput from(final GetAdminByIdOutput output) {
        return new GetAdminOutput(
                output.name(),
                output.email(),
                output.active(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
