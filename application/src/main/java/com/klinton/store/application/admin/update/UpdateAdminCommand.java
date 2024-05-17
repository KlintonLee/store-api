package com.klinton.store.application.admin.update;

public record UpdateAdminCommand(
        String id,
        String name,
        String email,
        boolean active
) {

    public static UpdateAdminCommand of(
            final String id,
            final String name,
            final String email,
            final boolean active
    ) {
        return new UpdateAdminCommand(id, name, email, active);
    }
}
