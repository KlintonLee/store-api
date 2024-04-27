package com.klinton.store.domain.product;

import com.klinton.store.domain.validation.Error;
import com.klinton.store.domain.validation.ValidationHandler;
import com.klinton.store.domain.validation.Validator;

import java.util.Objects;

public class ProductValidator extends Validator {

    private final Product product;

    protected ProductValidator(final Product product, final ValidationHandler aHandler) {
        super(aHandler);
        this.product = Objects.requireNonNull(product);
    }

    @Override
    public void validate() {
        final var name = product.getName();
        if (name == null || name.isBlank()) {
            validationHandler().append(new Error("Name should not be null or empty"));
            return;
        }

        if (product.getPrice() < 0) {
            validationHandler().append(new Error("Price should be greater than zero"));
        }
    }
}
