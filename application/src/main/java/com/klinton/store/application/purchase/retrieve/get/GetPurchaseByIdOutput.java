package com.klinton.store.application.purchase.retrieve.get;

import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseStatus;

import java.time.Instant;

public record GetPurchaseByIdOutput(
        String id,
        String customerId,
        String addressId,
        Instant purchaseDate,
        double totalPrice,
        String paymentMethod,
        PurchaseStatus status,
        Instant updatedAt
) {

    public static GetPurchaseByIdOutput from(final Purchase purchase) {
        return new GetPurchaseByIdOutput(
                purchase.id(),
                purchase.customerId(),
                purchase.addressId(),
                purchase.purchaseDate(),
                purchase.totalPrice(),
                purchase.paymentMethod(),
                purchase.status(),
                purchase.updatedAt()
        );
    }
}
