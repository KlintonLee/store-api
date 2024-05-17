package com.klinton.store.application.product.retrieve.list;

import com.klinton.store.domain.core.product.Product;

public record ListProductOutput(
        String id,
        String name,
        String description,
        int quantity,
        double price,
        boolean active
) {
    public static ListProductOutput from(Product product) {
        return new ListProductOutput(
                product.getId().getValue(),
                product.getName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.isActive()
        );
    }
}
