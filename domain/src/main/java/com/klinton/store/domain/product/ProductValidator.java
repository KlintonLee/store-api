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
        if (validateName()) return;
        if (validatePrice()) return;
        validateQuantity();
    }

    private boolean validateName() {
        final var name = product.getName();

        if (name == null || name.isBlank()) {
            validationHandler().append(new Error("Name should not be null or empty"));
            return true;
        }
        return false;
    }

    private boolean validatePrice() {
        if (product.getPrice() < 0) {
            validationHandler().append(new Error("Price should be greater than zero"));
            return true;
        }
        return false;
    }

    private void validateQuantity() {
        if (product.getQuantity() < 0) {
            validationHandler().append(new Error("Quantity should be greater than zero"));
        }
    }
}
