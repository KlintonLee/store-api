package com.klinton.store.application.purchase.create;

import java.time.Instant;

public record CreatePurchaseCommand(
        String customerId,
        String addressId,
        Instant purchaseDate,
        double totalPrice,
        String paymentMethod
) {

    public static CreatePurchaseCommand with(
            final String customerId,
            final String addressId,
            final Instant purchaseDate,
            final double totalPrice,
            final String paymentMethod
    ) {
        return new CreatePurchaseCommand(customerId, addressId, purchaseDate, totalPrice, paymentMethod);
    }
}
