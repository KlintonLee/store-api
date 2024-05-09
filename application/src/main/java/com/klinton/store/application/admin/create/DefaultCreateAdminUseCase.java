package com.klinton.store.application.admin.create;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import com.klinton.store.domain.validation.Notification;

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

        final var notification = Notification.create();
        final var admin = Admin.create(name, email, password, active);
        admin.validate(notification);

        if (notification.hasError()) {
            var messageError = Utils.mountErrorMessage(notification);
            throw new UnprocessableEntityException(messageError);
        }
        adminGateway.save(admin);
        return CreateAdminOutput.from(admin);
    }
}
