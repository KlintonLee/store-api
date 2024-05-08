package com.klinton.store.application.admin.create;

import com.klinton.store.domain.core.admin.Admin;

public record CreateAdminOutput(
        String id
) {

    public static CreateAdminOutput from(final String id) {
        return new CreateAdminOutput(id);
    }

    public static CreateAdminOutput from(final Admin admin) {
        return new CreateAdminOutput(admin.getId().getValue());
    }
}
