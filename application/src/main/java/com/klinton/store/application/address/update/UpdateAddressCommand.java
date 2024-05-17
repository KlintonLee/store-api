package com.klinton.store.application.address.update;

import com.klinton.store.domain.core.address.States;

public record UpdateAddressCommand(
        String id,
        String customerId,
        String street,
        String city,
        String neighborhood,
        States state,
        String number,
        String zipCode
) {
    public static UpdateAddressCommand of(
            String id,
            String customerId,
            String street,
            String city,
            String neighborhood,
            States state,
            String number,
            String zipCode
    ) {
        return new UpdateAddressCommand(id, customerId, street, city, neighborhood, state, number, zipCode);
    }
}
