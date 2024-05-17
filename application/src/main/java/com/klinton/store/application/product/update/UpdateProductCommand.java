package com.klinton.store.application.product.update;

public record UpdateProductCommand(
        String id,
        String name,
        String description,
        int quantity,
        double price,
        boolean active
) {
    public static UpdateProductCommand of(
            String id,
            String name,
            String description,
            int quantity,
            double price,
            boolean active
    ) {
        return new UpdateProductCommand(id, name, description, quantity, price, active);
    }
}
