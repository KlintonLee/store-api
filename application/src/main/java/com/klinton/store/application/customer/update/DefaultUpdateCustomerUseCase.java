package com.klinton.store.application.customer.update;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.customer.CustomerID;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import com.klinton.store.domain.validation.Notification;

import java.util.Objects;

public class DefaultUpdateCustomerUseCase extends UpdateCustomerUseCase {

    private final CustomerGateway customerGateway;

    public DefaultUpdateCustomerUseCase(final CustomerGateway customerGateway) {
        this.customerGateway = Objects.requireNonNull(customerGateway);
    }

    @Override
    public UpdateCustomerOutput execute(UpdateCustomerCommand command) {
        final var customerId = CustomerID.from(command.id());
        final var customer = customerGateway.getById(customerId)
                .orElseThrow(Utils.notFound(customerId, Customer.class));
        customer.update(
                command.name(),
                command.email(),
                command.password(),
                command.phone(),
                command.active()
        );

        final var notification = Notification.create();
        customer.validate(notification);

        if (notification.hasError()) {
            final var errorMessage = Utils.mountErrorMessage(notification);
            throw new UnprocessableEntityException(errorMessage);
        }

        final var updatedCustomer = customerGateway.save(customer);
        return new UpdateCustomerOutput(updatedCustomer.getId().getValue());
    }
}
