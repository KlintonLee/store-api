package com.klinton.store.application.cart.create;

public record CreateCartCommand(
        String customerId,
        String productId,
        Integer quantity,
        Double price
) {
    public static CreateCartCommand of(
            String customerId,
            String productId,
            Integer quantity,
            Double price
    ) {
        return new CreateCartCommand(customerId, productId, quantity, price);
    }
}
