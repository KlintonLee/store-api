package com.klinton.store.application.customer.retrieve.get;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.customer.CustomerID;

import java.util.Objects;

public class DefaultGetCustomerByIdUseCase extends GetCustomerByIdUseCase {

    private final CustomerGateway customerGateway;

    public DefaultGetCustomerByIdUseCase(final CustomerGateway customerGateway) {
        this.customerGateway = Objects.requireNonNull(customerGateway);
    }

    @Override
    public GetCustomerByIdOutput execute(String id) {
        final var customerId = CustomerID.from(id);
        final var customer = customerGateway.getById(customerId)
                .orElseThrow(Utils.notFound(customerId, Customer.class));

        return GetCustomerByIdOutput.from(customer);
    }
}
