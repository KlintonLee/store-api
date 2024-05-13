package com.klinton.store.application.address.create;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.customer.CustomerID;

import java.util.Objects;

public class DefaultCreateAddressUseCase extends CreateAddressUseCase {

    private final AddressGateway addressGateway;

    private final CustomerGateway customerGateway;

    public DefaultCreateAddressUseCase(final AddressGateway addressGateway, final CustomerGateway customerGateway) {
        this.addressGateway = Objects.requireNonNull(addressGateway);
        this.customerGateway = Objects.requireNonNull(customerGateway);
    }

    @Override
    public CreateAddressOutput execute(CreateAddressCommand input) {
        final var customerId = CustomerID.from(input.customerId());
        customerGateway.getById(customerId)
                .orElseThrow(Utils.notFound(customerId, Customer.class));

        final var address = Address.create(
                input.street(),
                input.city(),
                input.neighborhood(),
                input.state(),
                input.number(),
                input.zipCode()
        );

        return CreateAddressOutput.from(addressGateway.save(address));
    }
}
