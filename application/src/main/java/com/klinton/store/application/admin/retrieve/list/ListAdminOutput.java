package com.klinton.store.application.admin.retrieve.list;

import com.klinton.store.domain.core.admin.Admin;

public record ListAdminOutput(
        String id,
        String name,
        String email,
        boolean active
) {

    public static ListAdminOutput from(Admin admin) {
        return new ListAdminOutput(
                admin.getId().getValue(),
                admin.getName(),
                admin.getEmail(),
                admin.isActive()
        );
    }
}
