package com.klinton.store.application.product.create;

import com.klinton.store.domain.core.product.Product;

public record CreateProductOutput(
        String id
) {
    public static CreateProductOutput from(final String id) {
        return new CreateProductOutput(id);
    }

    public static CreateProductOutput from(final Product product) {
        return new CreateProductOutput(product.getId().getValue());
    }
}
