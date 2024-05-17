package com.klinton.store.application.purchase.create;

import com.klinton.store.domain.core.purchase.PurchaseStatus;

public record CreatePurchaseCommand(
        String customerId,
        String addressId,
        double totalPrice,
        String paymentMethod,
        PurchaseStatus status
) {

    public static CreatePurchaseCommand of(
            final String customerId,
            final String addressId,
            final double totalPrice,
            final String paymentMethod,
            final PurchaseStatus status
            ) {
        return new CreatePurchaseCommand(customerId, addressId, totalPrice, paymentMethod, status);
    }
}
