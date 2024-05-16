package com.klinton.store.application.purchase.update;

import com.klinton.store.domain.core.purchase.Purchase;

public record UpdatePurchaseOutput(
        String id
) {

    public static UpdatePurchaseOutput from(String id) {
        return new UpdatePurchaseOutput(id);
    }

    public static UpdatePurchaseOutput from(Purchase purchase) {
        return new UpdatePurchaseOutput(purchase.id());
    }
}
