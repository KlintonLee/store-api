package com.klinton.store.application.address.update;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.AddressId;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.customer.CustomerID;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import com.klinton.store.domain.validation.Notification;

import java.util.Objects;

public class DefaultUpdateAddressUseCase extends UpdateAddressUseCase {

    private final AddressGateway addressGateway;

    private final CustomerGateway customerGateway;

    public DefaultUpdateAddressUseCase(final AddressGateway addressGateway, final CustomerGateway customerGateway) {
        this.addressGateway = Objects.requireNonNull(addressGateway);
        this.customerGateway = Objects.requireNonNull(customerGateway);
    }

    @Override
    public UpdateAddressOutput execute(UpdateAddressCommand command) {
        final var addressId = AddressId.from(command.id());
        final var address = addressGateway.getById(addressId)
                .orElseThrow(Utils.notFound(addressId, Address.class));

        address.update(
                command.customerId(),
                command.street(),
                command.city(),
                command.neighborhood(),
                command.state(),
                command.number(),
                command.zipCode()
        );

        final var notification = Notification.create();
        address.validate(notification);

        if (notification.hasError()) {
            final var errorMessage = Utils.mountErrorMessage(notification);
            throw new UnprocessableEntityException(errorMessage);
        }

        final var customerId = CustomerID.from(command.customerId());
        customerGateway.getById(customerId)
                .orElseThrow(Utils.notFound(customerId, Customer.class));

        return UpdateAddressOutput.from(addressGateway.save(address));
    }
}
