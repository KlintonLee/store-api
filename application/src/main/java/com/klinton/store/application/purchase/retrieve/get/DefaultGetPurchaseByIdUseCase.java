package com.klinton.store.application.purchase.retrieve.get;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseGateway;
import com.klinton.store.domain.core.purchase.PurchaseId;

import java.util.Objects;

public class DefaultGetPurchaseByIdUseCase extends GetPurchaseByIdUseCase {

    private final PurchaseGateway purchaseGateway;

    public DefaultGetPurchaseByIdUseCase(final PurchaseGateway purchaseGateway) {
        this.purchaseGateway = Objects.requireNonNull(purchaseGateway);
    }


    @Override
    public GetPurchaseByIdOutput execute(String id) {
        final var purchaseId = PurchaseId.from(id);
        final var purchase = purchaseGateway.getByID(purchaseId)
                .orElseThrow(Utils.notFound(purchaseId, Purchase.class));

        return GetPurchaseByIdOutput.from(purchase);
    }
}
