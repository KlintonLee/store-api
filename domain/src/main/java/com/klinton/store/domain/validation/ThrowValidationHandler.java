package com.klinton.store.domain.validation;

import com.klinton.store.domain.exception.DomainException;

import java.util.List;

public class ThrowValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(Error anError) {
        throw new DomainException(anError.message());
    }

    @Override
    public ValidationHandler append(ValidationHandler anHandler) {
        throw new DomainException(anHandler.firstError().message());
    }

    @Override
    public <T> T validate(Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final Exception ex) {
            throw new DomainException(ex.getMessage());
        }
    }

    @Override
    public List<Error> getErrors() {
        return null;
    }
}
