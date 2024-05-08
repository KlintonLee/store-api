package com.klinton.store.application.admin.create;

public record CreateAdminCommand(
        String name,
        String email,
        String password,
        boolean active
) {

    public static CreateAdminCommand with(
            final String aName,
            final String anEmail,
            final String aPassword,
            final boolean isActive
    ) {
        return new CreateAdminCommand(aName, anEmail, aPassword, isActive);
    }
}
