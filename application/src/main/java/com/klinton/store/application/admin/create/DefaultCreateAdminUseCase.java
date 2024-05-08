package com.klinton.store.application.admin.create;

import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import com.klinton.store.domain.validation.Error;
import com.klinton.store.domain.validation.Notification;

import java.util.Objects;
import java.util.stream.Collectors;

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
            unprocessableEntity(notification);
        }
        adminGateway.save(admin);
        return CreateAdminOutput.from(admin);
    }

    private static void unprocessableEntity(Notification notification) {
        final var errorMessage = notification.getErrors()
                .stream()
                .map(Error::message)
                .collect(Collectors.joining("; "));
        throw new UnprocessableEntityException(errorMessage);
    }
}
