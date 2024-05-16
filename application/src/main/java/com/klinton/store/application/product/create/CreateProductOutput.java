package com.klinton.store.application.product.create;

import com.klinton.store.domain.core.product.Product;

public record CreateProductOutput(
        String id
) {
    public static CreateProductOutput with(final String id) {
        return new CreateProductOutput(id);
    }

    public static CreateProductOutput with(final Product product) {
        return new CreateProductOutput(product.getId().getValue());
    }
}
