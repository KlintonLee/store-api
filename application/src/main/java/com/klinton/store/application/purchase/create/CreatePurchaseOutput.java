package com.klinton.store.application.purchase.create;

import com.klinton.store.domain.core.purchase.Purchase;

public record CreatePurchaseOutput(
        String id
) {

    public static CreatePurchaseOutput with(final String id) {
        return new CreatePurchaseOutput(id);
    }

    public static CreatePurchaseOutput with(final Purchase purchase) {
        return new CreatePurchaseOutput(purchase.id());
    }
}
