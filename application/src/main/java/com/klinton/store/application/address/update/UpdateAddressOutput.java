package com.klinton.store.application.address.update;

import com.klinton.store.domain.core.address.Address;

public record UpdateAddressOutput(
        String id
) {
    public static UpdateAddressOutput from(String id) {
        return new UpdateAddressOutput(id);
    }

    public static UpdateAddressOutput from(Address address) {
        return new UpdateAddressOutput(address.getId().getValue());
    }
}
