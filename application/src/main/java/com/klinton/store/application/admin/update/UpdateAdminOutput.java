package com.klinton.store.application.admin.update;

import com.klinton.store.domain.core.admin.Admin;

public record UpdateAdminOutput(
        String id
) {
    public static UpdateAdminOutput from(final String id) {
        return new UpdateAdminOutput(id);
    }

    public static UpdateAdminOutput from(final Admin admin) {
        return new UpdateAdminOutput(admin.getId().getValue());
    }
}
