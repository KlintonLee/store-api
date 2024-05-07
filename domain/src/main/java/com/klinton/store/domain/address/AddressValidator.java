package com.klinton.store.domain.address;

import com.klinton.store.domain.validation.Error;
import com.klinton.store.domain.validation.ValidationHandler;
import com.klinton.store.domain.validation.Validator;

import java.util.Objects;

public class AddressValidator extends Validator {

    private final Address address;

    public AddressValidator(Address address, ValidationHandler handler) {
        super(Objects.requireNonNull(handler));
        this.address = Objects.requireNonNull(address);
    }

    @Override
    public void validate() {
        final var street = address.getStreet();
        if (street == null || street.isBlank()) {
            validationHandler().append(new Error("Street name should not be null or empty"));
        }
    }
}
