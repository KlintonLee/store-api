package com.klinton.store.application.product.create;

public record CreateProductCommand(
        String name,
        String description,
        int quantity,
        double price
) {
    public static CreateProductCommand of(
            final String name,
            final String description,
            final int quantity,
            final double price
    ) {
        return new CreateProductCommand(name, description, quantity, price);
    }
}
