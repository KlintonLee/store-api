package com.klinton.store.domain.users.admin;

import com.klinton.store.domain.validation.Error;
import com.klinton.store.domain.validation.ValidationHandler;
import com.klinton.store.domain.validation.Validator;

public class AdminValidator extends Validator {

    private final Admin admin;

    protected AdminValidator(final Admin admin, final ValidationHandler aHandler) {
        super(aHandler);
        this.admin = admin;
    }

    @Override
    public void validate() {
        validateName();
        validateEmail();
    }

    private void validateName() {
        final var name = admin.getName();

        if (name == null || name.isBlank()) {
            validationHandler().append(new Error("Name should not be null or empty"));
        }
    }

    private void validateEmail() {
        final var email = admin.getEmail();
        if (email == null || email.isBlank()) {
            validationHandler().append(new Error("Email should not be null or empty"));
        }
    }
}
