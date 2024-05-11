package com.klinton.store.application.customer.delete;

import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.customer.CustomerID;

import java.util.Objects;

public class DefaultDeleteCustomerUseCase extends DeleteCustomerUseCase {

    private final CustomerGateway customerGateway;

    public DefaultDeleteCustomerUseCase(final CustomerGateway customerGateway) {
        this.customerGateway = Objects.requireNonNull(customerGateway);
    }

    @Override
    public void execute(String id) {
        final var customerId = CustomerID.from(id);
        final var optionalCustomer = customerGateway.getById(customerId);

        optionalCustomer.ifPresent(customer -> {
            customer.deactivate();
            customerGateway.save(customer);
        });
    }
}
