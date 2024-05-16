package com.klinton.store.application.purchase.update;

import com.klinton.store.domain.core.purchase.PurchaseStatus;

public record UpdatePurchaseCommand(
        String purchaseId,
        String addressId,
        double totalPrice,
        String paymentMethod,
        PurchaseStatus status
) {
    public static UpdatePurchaseCommand with(
            String purchaseId,
            String addressId,
            double totalPrice,
            String paymentMethod,
            PurchaseStatus status
    ) {
        return new UpdatePurchaseCommand(purchaseId, addressId, totalPrice, paymentMethod, status);
    }
}
