package com.klinton.store.application.purchase.create;

import com.klinton.store.domain.core.purchase.Purchase;

public record CreatePurchaseOutput(
        String id
) {

    public static CreatePurchaseOutput from(final String id) {
        return new CreatePurchaseOutput(id);
    }

    public static CreatePurchaseOutput from(final Purchase purchase) {
        return new CreatePurchaseOutput(purchase.id());
    }
}
