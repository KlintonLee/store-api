package com.klinton.store.application.admin.retrieve.get;

import com.klinton.store.domain.core.admin.Admin;

import java.time.Instant;

public record GetAdminByIdOutput(
    String id,
    String name,
    String email,
    String password,
    boolean active,
    Instant createdAt,
    Instant updatedAt,
    Instant deletedAt
) {
    public static GetAdminByIdOutput from(Admin admin) {
        return new GetAdminByIdOutput(
                admin.getId().getValue(),
                admin.getName(),
                admin.getEmail(),
                admin.getPassword(),
                admin.isActive(),
                admin.getCreatedAt(),
                admin.getUpdatedAt(),
                admin.getDeletedAt()
        );
    }
}
