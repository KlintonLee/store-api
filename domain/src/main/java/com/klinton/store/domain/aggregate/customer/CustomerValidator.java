package com.klinton.store.domain.aggregate.customer;

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
        validateName();
        validateEmail();
    }



    private void validateName() {
        var name = customer.getName();
        if (name == null || name.isBlank()) {
            this.validationHandler().append(new Error("Name should not be null or empty"));
        }
    }

    private void validateEmail() {
        var email = customer.getEmail();
        if (email == null || email.isBlank()) {
            this.validationHandler().append(new Error("Email should not be null or empty"));
        }
    }
}
