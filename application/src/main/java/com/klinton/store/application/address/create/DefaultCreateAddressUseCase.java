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
    public CreateAddressOutput execute(CreateAddressCommand command) {
        final var customerId = CustomerID.from(command.customerId());
        customerGateway.getById(customerId)
                .orElseThrow(Utils.notFound(customerId, Customer.class));

        final var address = Address.create(
                customerId.getValue(),
                command.street(),
                command.city(),
                command.neighborhood(),
                command.state(),
                command.number(),
                command.zipCode()
        );

        return CreateAddressOutput.from(addressGateway.save(address));
    }
}
