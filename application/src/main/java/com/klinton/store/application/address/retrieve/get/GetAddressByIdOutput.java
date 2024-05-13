package com.klinton.store.application.address.retrieve.get;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.States;

public record GetAddressByIdOutput(
    String id,
    String customerId,
    String street,
    String number,
    String neighborhood,
    String city,
    States state,
    String zipCode
) {
    public static GetAddressByIdOutput from(Address address) {
        return new GetAddressByIdOutput(
            address.getId().getValue(),
            address.getCustomerId(),
            address.getStreet(),
            address.getNumber(),
            address.getNeighborhood(),
            address.getCity(),
            address.getState(),
            address.getZipCode()
        );
    }
}
