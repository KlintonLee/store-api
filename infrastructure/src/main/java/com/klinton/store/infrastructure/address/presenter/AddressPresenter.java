package com.klinton.store.infrastructure.address.presenter;

import com.klinton.store.application.address.retrieve.get.GetAddressByIdOutput;

public interface AddressPresenter {

    static GetAddressResponse present(GetAddressByIdOutput output) {
        return new GetAddressResponse(
                output.id(),
                output.customerId(),
                output.street(),
                output.number(),
                output.neighborhood(),
                output.city(),
                output.state(),
                output.zipCode()
        );
    }
}
