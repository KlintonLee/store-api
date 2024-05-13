package com.klinton.store.application.address.create;

import com.klinton.store.domain.core.address.Address;

public record CreateAddressOutput(
        String id
) {
    public static CreateAddressOutput from(String id) {
        return new CreateAddressOutput(id);
    }

    public static CreateAddressOutput from(Address address) {
        return new CreateAddressOutput(address.getId().getValue());
    }
}
