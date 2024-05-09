package com.klinton.store.application.admin.update;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.core.admin.AdminID;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import com.klinton.store.domain.validation.Notification;

import java.util.Objects;

public class DefaultUpdateAdminUseCase extends UpdateAdminUseCase {

    private final AdminGateway adminGateway;

    public DefaultUpdateAdminUseCase(AdminGateway adminGateway) {
        this.adminGateway = Objects.requireNonNull(adminGateway);
    }

    @Override
    public UpdateAdminOutput execute(UpdateAdminCommand command) {
        final var adminId = AdminID.from(command.id());
        final var admin = adminGateway.getById(adminId)
                .orElseThrow(Utils.notFound(adminId, Admin.class));

        final var name = command.name();
        final var email = command.email();
        final var password = command.password();
        final var active = command.active();
        admin.update(name, email, password, active);

        final var notification = Notification.create();
        admin.validate(notification);

        if (notification.hasError()) {
            var messageError = Utils.mountErrorMessage(notification);
            throw new UnprocessableEntityException(messageError);
        }

        adminGateway.save(admin);
        return new UpdateAdminOutput(adminId.getValue());
    }
}
