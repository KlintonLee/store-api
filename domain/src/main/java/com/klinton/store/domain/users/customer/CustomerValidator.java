package com.klinton.store.domain.users.customer;

import com.klinton.store.domain.validation.Error;
import com.klinton.store.domain.validation.ValidationHandler;
import com.klinton.store.domain.validation.Validator;

import java.util.Objects;

public class CustomerValidator extends Validator {

    private final Customer customer;

    protected CustomerValidator(final ValidationHandler aHandler, final Customer customer) {
        super(aHandler);
        this.customer = Objects.requireNonNull(customer);
    }

    @Override
    public void validate() {
        var name = customer.getName();
        if (name == null || name.isBlank()) {
            this.validationHandler().append(new Error("Name should not be null or empty"));
        }
    }
}
