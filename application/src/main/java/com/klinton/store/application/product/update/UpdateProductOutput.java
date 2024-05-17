package com.klinton.store.application.product.update;

import com.klinton.store.domain.core.product.Product;

public record UpdateProductOutput(
        String id
) {
    public static UpdateProductOutput from(String id) {
        return new UpdateProductOutput(id);
    }

    public static UpdateProductOutput from(Product product) {
        return new UpdateProductOutput(product.getId().getValue());
    }
}
