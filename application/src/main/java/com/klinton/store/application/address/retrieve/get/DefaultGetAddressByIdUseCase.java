package com.klinton.store.application.address.retrieve.get;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.AddressId;

import java.util.Objects;

public class DefaultGetAddressByIdUseCase extends GetAddressByIdUseCase {

    private final AddressGateway addressGateway;

    public DefaultGetAddressByIdUseCase(final AddressGateway addressGateway) {
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Override
    public GetAddressByIdOutput execute(String id) {
        final var addressId = AddressId.from(id);
        final var address = addressGateway.getById(addressId)
                .orElseThrow(Utils.notFound(addressId, Address.class));

        return GetAddressByIdOutput.from(address);
    }
}
