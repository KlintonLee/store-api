package com.klinton.store.infrastructure.purchase.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.States;

public record GetPurchaseAddressResponse(
        @JsonProperty("id") String id,
        @JsonProperty("street") String street,
        @JsonProperty("number") String number,
        @JsonProperty("neighborhood") String neighborhood,
        @JsonProperty("city") String city,
        @JsonProperty("state") States state,
        @JsonProperty("zip_code") String zipCode
) {
    public static GetPurchaseAddressResponse from(final Address address) {
        if (address == null) {
            return null;
        }

        return new GetPurchaseAddressResponse(
                address.getId().getValue(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }
}
