package com.klinton.store.application.admin.delete;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.core.admin.AdminID;

import java.util.Objects;

public class DefaultDeleteAdminUseCase extends DeleteAdminUseCase {

    private final AdminGateway adminGateway;

    public DefaultDeleteAdminUseCase(final AdminGateway adminGateway) {
        this.adminGateway = Objects.requireNonNull(adminGateway);
    }

    @Override
    public void execute(String input) {
        final var adminID = AdminID.from(input);
        final var admin = adminGateway.getById(adminID).orElseThrow(Utils.notFound(adminID, AdminID.class));

        admin.deactivate();
        adminGateway.update(admin);
    }
}
