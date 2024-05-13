package com.klinton.store.application.address.delete;

import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.AddressId;

import java.util.Objects;

public class DefaultDeleteAddressUseCase extends DeleteAddressUseCase {

    private final AddressGateway addressGateway;

    public DefaultDeleteAddressUseCase(final AddressGateway addressGateway) {
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Override
    public void execute(String id) {
        final var addressId = AddressId.from(id);
        this.addressGateway.delete(addressId);
    }
}
