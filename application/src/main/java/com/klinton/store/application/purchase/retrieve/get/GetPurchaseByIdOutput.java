package com.klinton.store.application.purchase.retrieve.get;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseStatus;

import java.time.Instant;

public record GetPurchaseByIdOutput(
        String id,
        Customer customer,
        Address address,
        Instant purchaseDate,
        double totalPrice,
        String paymentMethod,
        PurchaseStatus status,
        Instant updatedAt
) {

    public static GetPurchaseByIdOutput from(final Purchase purchase, final Customer customer, final Address address) {
        return new GetPurchaseByIdOutput(
                purchase.id(),
                customer,
                address,
                purchase.purchaseDate(),
                purchase.totalPrice(),
                purchase.paymentMethod(),
                purchase.status(),
                purchase.updatedAt()
        );
    }
}
