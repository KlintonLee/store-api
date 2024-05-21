package com.klinton.store.application.cart.update;

public record UpdateCartCommand(
        String id,
        int quantity,
        double price
) {
    public static UpdateCartCommand of(
            final String id,
            final int quantity,
            final double price
    ) {
        return new UpdateCartCommand(id, quantity, price);
    }
}
