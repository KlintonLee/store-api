package com.klinton.store.application.address.retrieve.list;

import com.klinton.store.domain.core.address.Address;

public record ListAddressOutput(
        String id,
        String customerId,
        String street,
        String city,
        String neighborhood,
        String state,
        String number,
        String zipCode
) {

    public static ListAddressOutput from(
            Address address
    ) {
        return new ListAddressOutput(
                address.getId().getValue(),
                address.getCustomerId(),
                address.getStreet(),
                address.getCity(),
                address.getNeighborhood(),
                address.getState().name(),
                address.getNumber(),
                address.getZipCode()
        );
    }
}
