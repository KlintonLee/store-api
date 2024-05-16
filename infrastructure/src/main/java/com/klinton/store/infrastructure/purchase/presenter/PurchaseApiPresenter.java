package com.klinton.store.infrastructure.purchase.presenter;

import com.klinton.store.application.purchase.retrieve.get.GetPurchaseByIdOutput;

public interface PurchaseApiPresenter {

    static GetPurchaseResponse from(GetPurchaseByIdOutput output) {
        return new GetPurchaseResponse(
                output.id(),
                output.customerId(),
                output.addressId(),
                output.purchaseDate(),
                output.totalPrice(),
                output.paymentMethod(),
                output.status().toString(),
                output.updatedAt()
        );

    }
}
