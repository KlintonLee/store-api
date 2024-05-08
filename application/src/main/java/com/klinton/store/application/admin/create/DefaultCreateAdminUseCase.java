package com.klinton.store.application.admin.create;

import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;

import java.util.Objects;

public class DefaultCreateAdminUseCase extends CreateAdminUseCase {

    private final AdminGateway adminGateway;

    public DefaultCreateAdminUseCase(final AdminGateway adminGateway) {
        this.adminGateway = Objects.requireNonNull(adminGateway);
    }

    @Override
    public CreateAdminOutput execute(CreateAdminCommand command) {
        final var name = command.name();
        final var email = command.email();
        final var password = command.password();
        final var active = command.active();

        final var admin = adminGateway.create(Admin.create(name, email, password, active));

        return CreateAdminOutput.from(admin);
    }
}
