package com.klinton.store.application.admin.update;

public record UpdateAdminCommand(
        String id,
        String name,
        String email,
        String password,
        boolean active
) {

    public static UpdateAdminCommand with(
            final String id,
            final String name,
            final String email,
            final String password,
            final boolean active
    ) {
        return new UpdateAdminCommand(id, name, email, password, active);
    }
}
