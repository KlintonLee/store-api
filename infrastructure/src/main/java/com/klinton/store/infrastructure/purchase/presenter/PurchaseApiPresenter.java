package com.klinton.store.infrastructure.purchase.presenter;

import com.klinton.store.application.purchase.retrieve.get.GetPurchaseByIdOutput;

public interface PurchaseApiPresenter {

    static GetPurchaseResponse from(GetPurchaseByIdOutput output) {
        final var customer = GetPurchaseCustomerResponse.from(output.customer());

        final var address = GetPurchaseAddressResponse.from(output.address());

        return new GetPurchaseResponse(
                output.id(),
                customer,
                address,
                output.purchaseDate(),
                output.totalPrice(),
                output.paymentMethod(),
                output.status().toString(),
                output.updatedAt()
        );

    }
}
