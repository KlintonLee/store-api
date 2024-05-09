package com.klinton.store.application.admin.retrieve.get;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.core.admin.AdminID;

import java.util.Objects;

public class DefaultGetAdminByIdUseCase extends GetAdminByIdUseCase {

    private final AdminGateway adminGateway;

    public DefaultGetAdminByIdUseCase(final AdminGateway adminGateway) {
        this.adminGateway = Objects.requireNonNull(adminGateway);
    }

    @Override
    public GetAdminByIdOutput execute(String id) {
        final var adminId = AdminID.from(id);
        final var admin = adminGateway
                .getById(adminId)
                .orElseThrow(Utils.notFound(adminId, Admin.class));

        return GetAdminByIdOutput.from(admin);
    }
}
