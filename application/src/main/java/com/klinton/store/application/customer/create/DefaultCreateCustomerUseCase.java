package com.klinton.store.application.customer.create;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import com.klinton.store.domain.validation.Notification;

import java.util.Objects;

public class DefaultCreateCustomerUseCase extends CreateCustomerUseCase {

    private final CustomerGateway customerGateway;

    public DefaultCreateCustomerUseCase(final CustomerGateway customerGateway) {
        this.customerGateway = Objects.requireNonNull(customerGateway);
    }

    @Override
    public CreateCustomerOutput execute(CreateCustomerCommand input) {
        final var name = input.name();
        final var email = input.email();
        final var password = input.password();
        final var phone = input.phone();
        final var customer = Customer.create(name, email, password, phone);

        final var notification = Notification.create();
        customer.validate(notification);

        if (notification.hasError()) {
            var messageError = Utils.mountErrorMessage(notification);
            throw new UnprocessableEntityException(messageError);
        }

        customerGateway.save(customer);
        return CreateCustomerOutput.from(customer);
    }
}
