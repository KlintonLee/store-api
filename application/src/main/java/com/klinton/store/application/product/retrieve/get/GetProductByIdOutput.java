package com.klinton.store.application.product.retrieve.get;

import com.klinton.store.domain.core.product.Product;

import java.time.Instant;

public record GetProductByIdOutput(
        String id,
        String name,
        String description,
        int quantity,
        double price,
        boolean active,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static GetProductByIdOutput from(Product product) {
        return new GetProductByIdOutput(
                product.getId().getValue(),
                product.getName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.isActive(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getDeletedAt()
        );
    }
}
