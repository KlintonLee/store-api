package com.klinton.store.application.address.create;

import com.klinton.store.domain.core.address.States;

public record CreateAddressCommand(
        String customerId,
        String street,
        String city,
        String neighborhood,
        States state,
        String number,
        String zipCode
) {
    public static CreateAddressCommand of(
            String customerId,
            String street,
            String city,
            String neighborhood,
            States state,
            String number,
            String zipCode
    ) {
        return new CreateAddressCommand(customerId, street, city, neighborhood, state, number, zipCode);
    }
}
