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
        final var name = admin.getName();

        if (name == null || name.isBlank()) {
            validationHandler().append(new Error("Name should not be null or empty"));
        }
    }
}
