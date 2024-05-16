package com.klinton.store.application.purchase.retrieve.list;

import com.klinton.store.domain.core.purchase.Purchase;

public record ListPurchaseOutput(
        String purchaseId,
        String customerId,
        String addressId,
        Double totalPrice,
        String paymentMethod,
        String status
) {

    public static ListPurchaseOutput from(Purchase purchase) {
        return new ListPurchaseOutput(
                purchase.id(),
                purchase.customerId(),
                purchase.addressId(),
                purchase.totalPrice(),
                purchase.paymentMethod(),
                purchase.status().name()
        );
    }
}
